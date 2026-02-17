package com.example.product;

import com.example.product.dto.ProductRequestDto;
import com.example.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository repository;

    @Test
    void shouldCreateProductThroughApi() {

         ProductRequestDto request =
                new ProductRequestDto(
                        "Integreation",
                        "Test",
                        new BigDecimal("500.00"),
                        "INT-001"
                );

        var response =
                restTemplate.postForEntity(
                        "http://localhost:" + port + "/api/v1/products",
                        request,
                        String.class
                );

        assertThat(response.getStatusCode().value()).isEqualTo(201);
        assertThat(repository.findAll()).isNotEmpty();
    }
}
