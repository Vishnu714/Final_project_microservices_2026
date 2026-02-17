package com.example.product.service;

import com.example.product.dto.ProductRequestDto;
import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;
import com.example.product.audit.AuditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

class ProductServiceImplTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private AuditService auditService;

    @InjectMocks
    private ProductServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateProduct() {

        ProductRequestDto request =
                new ProductRequestDto(
        "MacBook",
        "Laptop",
        new BigDecimal("2500.00"),
        "MB-001"
);


        Product savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setName("MacBook");
        savedProduct.setDescription("Laptop");
        savedProduct.setPrice(new BigDecimal("2500.00"));

        savedProduct.setSku("MB-001");
        savedProduct.setActive(true);

        when(repository.save(any(Product.class)))
                .thenReturn(savedProduct);

        var response = service.createProduct(request);

        assertThat(response.id()).isEqualTo(1L);

        assertThat(response.id()).isEqualTo(1L);


        verify(repository, times(1)).save(any(Product.class));
        verify(auditService, times(1))
                .log(eq("Product"), eq(1L), eq("CREATE"), anyLong());
    }
}
