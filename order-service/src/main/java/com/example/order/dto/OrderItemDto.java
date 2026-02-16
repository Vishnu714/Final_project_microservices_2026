package com.example.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemDto(
        @NotNull Long productId,
        @Min(1) Integer quantity,
        @Positive Double price
) {}
