package com.example.product.controller;

import com.example.product.service.ProductService;
import com.example.product.dto.ProductResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
void shouldCreateProduct() throws Exception {

    ProductResponseDto response =
            new ProductResponseDto(
                    1L,
                    "MacBook",
                    null,
                    new BigDecimal("2500.0"),
                    "MBP-001",
                    true
            );

    when(service.createProduct(any()))
            .thenReturn(response);

    mockMvc.perform(post("/api/v1/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {
                      "name": "MacBook",
                      "price": 2500.0,
                      "sku": "MBP-001"
                    }
                    """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1));
}

}
