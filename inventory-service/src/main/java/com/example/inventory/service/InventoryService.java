package com.example.inventory.service;

import com.example.inventory.dto.*;

public interface InventoryService {

    InventoryResponseDto getInventory(Long productId);

    InventoryResponseDto restock(RestockRequestDto request);

    InventoryResponseDto reduceStock(ReduceStockRequestDto request);
}
