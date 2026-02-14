package com.example.inventory.dto;

public record InventoryResponseDto(
        Long id,
        Long productId,
        Integer quantity
) {}
