package com.example.inventory.dto;

import jakarta.validation.constraints.*;

public record ReduceStockRequestDto(

        @NotNull Long productId,

        @NotNull
        @Positive
        Integer quantity
) {}
