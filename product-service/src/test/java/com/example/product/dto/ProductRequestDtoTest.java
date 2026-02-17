
package com.example.product.dto;

import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.math.BigDecimal;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ProductRequestDtoTest {

    private Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
void shouldFailWhenFieldsInvalid() {

    ProductRequestDto dto =
            new ProductRequestDto(
                    "",
                    "",
                    new BigDecimal("-10"),
                    ""
            );

    Set<ConstraintViolation<ProductRequestDto>> violations =
            validator.validate(dto);

    assertThat(violations).isNotEmpty();
}

@Test
void shouldPassWhenValid() {

    ProductRequestDto dto =
            new ProductRequestDto(
                    "MacBook",
                    "Apple Laptop",
                    new BigDecimal("2500"),
                    "MBP-001"
            );

    Set<ConstraintViolation<ProductRequestDto>> violations =
            validator.validate(dto);

    assertThat(violations).isEmpty();
}

}
