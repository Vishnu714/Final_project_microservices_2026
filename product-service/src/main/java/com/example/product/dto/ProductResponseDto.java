package com.example.product.dto;

import java.math.BigDecimal;

public record ProductResponseDto(
        Long id,
        String name,
        String description,
        BigDecimal price,
        String sku,
        Boolean active
) {}

