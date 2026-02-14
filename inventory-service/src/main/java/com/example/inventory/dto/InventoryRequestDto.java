package com.example.inventory.dto;

import jakarta.validation.constraints.*;

public record InventoryRequestDto(

        @NotNull(message = "Product ID is required")
        Long productId,

        @NotNull(message = "Quantity is required")
        @Min(value = 0, message = "Quantity cannot be negative")
        Integer quantity
) {}
