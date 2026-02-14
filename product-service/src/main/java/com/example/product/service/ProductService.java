package com.example.product.service;

import com.example.product.dto.ProductRequestDto;
import com.example.product.dto.ProductResponseDto;
import java.util.List;

public interface ProductService {

    ProductResponseDto createProduct(ProductRequestDto request);

    ProductResponseDto getProductById(Long id);

    List<ProductResponseDto> getAllProducts();

    ProductResponseDto updateProduct(Long id, ProductRequestDto request);

    void deleteProduct(Long id);

}
