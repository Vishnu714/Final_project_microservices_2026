package com.example.order.controller;

import com.example.order.dto.OrderRequestDto;
import com.example.order.dto.OrderResponseDto;
import com.example.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDto create(
            @Valid @RequestBody OrderRequestDto request) {

        return service.createOrder(request);
    }

    @GetMapping("/{id}")
    public OrderResponseDto get(@PathVariable Long id) {
        return service.getOrder(id);
    }

    @PutMapping("/{id}/status")
    public OrderResponseDto updateStatus(
            @PathVariable Long id,
            @RequestParam String newStatus) {
        return service.updateStatus(id, newStatus);
    }

    @GetMapping("/by-product/{productId}")
    public List<OrderResponseDto> getOrdersByProduct(@PathVariable Long productId) {
        return service.getOrdersByProduct(productId);
}

}
