package com.example.product.repository;

import com.example.product.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;

    @Test
    void shouldSaveAndFindProduct() {

       Product product = new Product();
product.setName("Test");
product.setDescription("Test Desc");
product.setPrice(new BigDecimal("100.00"));
product.setSku("T-001");
product.setActive(true);


        Product saved = repository.save(product);

        var found = repository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Test");
    }
}
