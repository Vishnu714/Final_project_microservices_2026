package com.example.product.service;

import com.example.product.dto.*;
import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;
import com.example.product.audit.AuditService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private AuditService auditService;

    @InjectMocks
    private ProductServiceImpl service;

    @Test
    void shouldCreateProduct() {

        ProductRequestDto request =
                new ProductRequestDto(
                        "MacBook",
                        "Laptop",
                        new BigDecimal("2500.00"),
                        "MBP-001"
                );

        Product saved = new Product();
        saved.setId(1L);
        saved.setName("MacBook");
        saved.setDescription("Laptop");
        saved.setPrice(new BigDecimal("2500.00"));
        saved.setSku("MBP-001");
        saved.setActive(true);

        when(repository.save(any(Product.class)))
                .thenReturn(saved);

        ProductResponseDto response =
                service.createProduct(request);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("MacBook");

        verify(repository, times(1)).save(any(Product.class));
        verify(auditService, times(1))
                .log(eq("Product"), eq(1L), eq("CREATE"), anyLong());
    }

    @Test
    void shouldReturnProductById() {

        Product product = new Product();
        product.setId(1L);
        product.setName("MacBook");
        product.setDescription("Laptop");
        product.setPrice(new BigDecimal("2500.00"));
        product.setSku("MBP-001");
        product.setActive(true);

        when(repository.findById(1L))
                .thenReturn(Optional.of(product));

        ProductResponseDto response =
                service.getProductById(1L);

        assertThat(response.name()).isEqualTo("MacBook");
    }
}
