package com.example.inventory.controller;

import com.example.inventory.dto.*;
import com.example.inventory.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    @GetMapping("/{productId}")
    public InventoryResponseDto getInventory(
            @PathVariable Long productId) {

        return service.getInventory(productId);
    }

    @PostMapping("/restock")
    public InventoryResponseDto restock(
            @Valid @RequestBody RestockRequestDto request) {

        return service.restock(request);
    }

    @PostMapping("/reduce")
    public InventoryResponseDto reduce(
            @Valid @RequestBody ReduceStockRequestDto request) {

        return service.reduceStock(request);
    }
}
