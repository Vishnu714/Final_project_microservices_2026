package com.example.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;

import java.util.List;

public record OrderRequestDto(
        @NotNull Long userId,
        @NotEmpty List<@Valid OrderItemDto> items,
        @NotBlank String shippingAddress
) {}
