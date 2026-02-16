package com.example.order.service;

import com.example.order.audit.AuditService;
import com.example.order.dto.OrderItemDto;
import com.example.order.dto.OrderRequestDto;
import com.example.order.dto.OrderResponseDto;
import com.example.order.model.Order;
import com.example.order.model.OrderItem;
import com.example.order.model.OrderStatus;
import com.example.order.model.Shipment;
import com.example.order.repository.OrderRepository;
import com.example.order.repository.OrderStatusRepository;

import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepo;
    private final OrderStatusRepository statusRepo;
    private final WebClient webClient;
    private final CircuitBreaker circuitBreaker;
    private final AuditService auditService;

    public OrderServiceImpl(OrderRepository orderRepo,
                            OrderStatusRepository statusRepo,
                            WebClient webClient,
                            CircuitBreakerFactory<?, ?> circuitBreakerFactory,
                            AuditService auditService) {
        this.orderRepo = orderRepo;
        this.statusRepo = statusRepo;
        this.webClient = webClient;
        this.circuitBreaker = circuitBreakerFactory.create("inventoryCircuitBreaker");
        this.auditService = auditService;
    }

    @Override
    public OrderResponseDto createOrder(OrderRequestDto request) {

        OrderStatus createdStatus = statusRepo
                .findByName("CREATED")
                .orElseThrow(() -> new RuntimeException("Order status CREATED not found"));

        Order order = new Order();
        order.setUserId(request.userId());
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(createdStatus);

        List<OrderItem> items = request.items()
                .stream()
                .map(dto -> {

                    // 🔥 Circuit breaker wrapped inventory call
                    circuitBreaker.run(() ->
                            webClient.post()
                                    .uri("lb://inventory-service/api/v1/inventory/reduce")
                                    .bodyValue(Map.of(
                                            "productId", dto.productId(),
                                            "quantity", dto.quantity()
                                    ))
                                    .retrieve()
                                    .bodyToMono(Void.class)
                                    .block(),
                            throwable -> {
                                throw new RuntimeException("Inventory service unavailable");
                            }
                    );

                    OrderItem item = new OrderItem();
                    item.setProductId(dto.productId());
                    item.setQuantity(dto.quantity());
                    item.setPrice(dto.price());
                    item.setOrder(order);
                    return item;
                })
                .toList();

        order.setItems(items);

        // 🚚 Shipment generation
        Shipment shipment = new Shipment();
        shipment.setAddress(request.shippingAddress());
        shipment.setTrackingNumber(UUID.randomUUID().toString());
        shipment.setOrder(order);
        order.setShipment(shipment);

        Order saved = orderRepo.save(order);

        // 🧾 Audit log
        auditService.log("Order", saved.getId(), "CREATE", request.userId());

        return mapToResponse(saved);
    }

    @Override
    public OrderResponseDto getOrder(Long id) {

        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return mapToResponse(order);
    }

    @Override
    public OrderResponseDto updateStatus(Long id, String newStatus) {

        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        String currentStatus = order.getStatus().getName();

        if (!isValidTransition(currentStatus, newStatus)) {
            throw new IllegalStateException(
                    "Invalid status transition from " + currentStatus + " to " + newStatus);
        }

        OrderStatus status = statusRepo.findByName(newStatus)
                .orElseThrow(() -> new RuntimeException("Status " + newStatus + " not found"));

        order.setStatus(status);
        Order updated = orderRepo.save(order);

        auditService.log("Order", updated.getId(), "STATUS_UPDATE", 1L);

        return mapToResponse(updated);
    }

    private boolean isValidTransition(String current, String next) {
        return ("CREATED".equals(current) && ("SHIPPED".equals(next) || "CANCELLED".equals(next))) ||
               ("SHIPPED".equals(current) && "DELIVERED".equals(next));
    }

    private OrderResponseDto mapToResponse(Order order) {
        return new OrderResponseDto(
                order.getId(),
                order.getStatus().getName(),
                order.getItems()
                        .stream()
                        .map(i ->
                                new OrderItemDto(
                                        i.getProductId(),
                                        i.getQuantity(),
                                        i.getPrice()))
                        .toList(),
                order.getShipment() != null
                        ? order.getShipment().getTrackingNumber()
                        : null
        );
    }

    @Override
    public List<OrderResponseDto> getOrdersByProduct(Long productId) {

        return orderRepo.findAll().stream()
                .filter(order ->
                        order.getItems().stream()
                            .anyMatch(i -> i.getProductId().equals(productId)))
                .map(this::mapToResponse)
                .toList();
    }


    @Component
    public class OrderStatusInitializer {

        public OrderStatusInitializer(OrderStatusRepository repo) {
            if (repo.count() == 0) {
            repo.save(new OrderStatus(null, "CREATED"));
            repo.save(new OrderStatus(null, "SHIPPED"));
            repo.save(new OrderStatus(null, "DELIVERED"));
            repo.save(new OrderStatus(null, "CANCELLED"));
            }
        }
    }


}
