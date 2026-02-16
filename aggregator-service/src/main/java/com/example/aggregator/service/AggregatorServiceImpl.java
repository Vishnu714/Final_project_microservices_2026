package com.example.aggregator.service;

import com.example.aggregator.dto.*;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpHeaders;
import java.util.Arrays;
import java.util.List;

@Service
public class AggregatorServiceImpl implements AggregatorService {

    private final WebClient webClient;
    private final CircuitBreakerFactory<?, ?> circuitBreakerFactory;

    public AggregatorServiceImpl(WebClient webClient,
				 CircuitBreakerFactory<?, ?> circuitBreakerFactory) {
	this.webClient = webClient;
	this.circuitBreakerFactory = circuitBreakerFactory;
    }

    @Override
    public AggregatedResponseDto getProductOverview(Long productId) {

	CircuitBreaker productCB =
		circuitBreakerFactory.create("productCircuitBreaker");

	CircuitBreaker inventoryCB =
		circuitBreakerFactory.create("inventoryCircuitBreaker");

	CircuitBreaker orderCB =
		circuitBreakerFactory.create("orderCircuitBreaker");

	ProductDetailsDto product =
		productCB.run(() ->
			webClient.get()
				.uri("lb://product-service/api/v1/products/" + productId)
				.retrieve()
				.bodyToMono(ProductDetailsDto.class)
				.block(),
			throwable -> null
		);

	Integer inventory =
        inventoryCB.run(() ->
            webClient.get()
                .uri("lb://inventory-service/api/v1/inventory/" + productId)
                .retrieve()
                .bodyToMono(InventoryResponseDto.class)
                .block()
            .   getQuantity(),
            throwable -> null
        );

	OrderSummaryDto[] orders =
		orderCB.run(() ->
			webClient.get()
				.uri("lb://order-service/api/v1/orders/by-product/" + productId)
				.retrieve()
				.bodyToMono(OrderSummaryDto[].class)
				.block(),
			throwable -> new OrderSummaryDto[0]
		);

	List<OrderSummaryDto> orderList =
		orders != null ? Arrays.asList(orders) : List.of();

	return new AggregatedResponseDto(
		product,
		inventory,
		orderList
	);
    }
}
