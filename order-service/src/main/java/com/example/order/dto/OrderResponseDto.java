package com.example.order.dto;

import java.util.List;

public record OrderResponseDto(
        Long orderId,
        String status,
        List<OrderItemDto> items,
        String trackingNumber
) {}
