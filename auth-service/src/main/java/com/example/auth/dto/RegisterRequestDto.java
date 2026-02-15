package com.example.auth.dto;

import jakarta.validation.constraints.*;

public record RegisterRequestDto(

        @NotBlank String username,

        @NotBlank String password,

        @Email String email
) {}
