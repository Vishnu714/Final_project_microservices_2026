package com.example.product.controller;

import com.example.product.dto.ProductRequestDto;
import com.example.product.dto.ProductResponseDto;
import com.example.product.service.ProductService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ConfigServerApplication {

    private final ProductService service;

    public ConfigServerApplication(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ProductResponseDto create(@Valid @RequestBody ProductRequestDto request) {
        return service.createProduct(request);
    }

    @GetMapping("/{id}")
    public ProductResponseDto getById(@PathVariable Long id) {
        return service.getProductById(id);
    }

    @GetMapping
    public List<ProductResponseDto> getAll() {
        return service.getAllProducts();
    }

    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Long id,
                                     @Valid @RequestBody ProductRequestDto request) {
        return service.updateProduct(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteProduct(id);
    }
}
