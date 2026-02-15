package com.example.product.service;

import com.example.product.dto.ProductRequestDto;
import com.example.product.dto.ProductResponseDto;
import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;
import com.example.product.exception.ProductNotFoundException;
import com.example.product.audit.AuditService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final AuditService auditService;

    public ProductServiceImpl(ProductRepository repository, AuditService auditService) {
        this.repository = repository;
        this.auditService = auditService;
    }

    @Override
    public ProductResponseDto createProduct(ProductRequestDto request) {

        Product product = new Product();
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setSku(request.sku());
        product.setActive(true);

        Product saved = repository.save(product);

        auditService.log("Product", saved.getId(), "CREATE", 1L);

        return mapToResponse(saved);
    }

    @Override
    public ProductResponseDto getProductById(Long id) {

        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        return mapToResponse(product);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {

        return repository.findByActiveTrue()
            .stream()
            .map(this::mapToResponse)
            .toList();
    }


    @Override
    public ProductResponseDto updateProduct(Long id, ProductRequestDto request) {

        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setSku(request.sku());

        Product updated = repository.save(product);

        auditService.log("Product", updated.getId(), "UPDATE", 1L);

        return mapToResponse(updated);
    }

    @Override
    public void deleteProduct(Long id) {

        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        product.setActive(false);   // soft delete
        repository.save(product);

        auditService.log("Product", product.getId(), "DELETE", 1L);
    }

    private ProductResponseDto mapToResponse(Product product) {

        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getSku(),
                product.getActive()
        );
    }
}
