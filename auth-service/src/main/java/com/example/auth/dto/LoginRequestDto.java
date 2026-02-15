package com.example.auth.dto;

import jakarta.validation.constraints.*;

public record LoginRequestDto(

        @NotBlank String username,

        @NotBlank String password
) {}
