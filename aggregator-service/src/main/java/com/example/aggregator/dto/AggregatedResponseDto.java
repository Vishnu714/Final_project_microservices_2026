package com.example.aggregator.dto;

import java.util.List;

public record AggregatedResponseDto(
	ProductDetailsDto product,
	Object inventoryQuantity,
	List<OrderSummaryDto> orders
) {}
