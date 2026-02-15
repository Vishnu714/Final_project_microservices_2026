package com.example.auth.dto;

public record AuthResponseDto(
        String token,
        String username
) {}
