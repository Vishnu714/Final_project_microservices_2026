package com.example.inventory.dto;

import jakarta.validation.constraints.*;

public record RestockRequestDto(

        @NotNull Long productId,

        @NotNull
        @Positive
        Integer quantity
) {}
