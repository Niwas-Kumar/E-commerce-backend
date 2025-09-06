package com.example.ecommerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AuthDtos {
    public record RegisterRequest(@NotBlank String name, @Email String email, @NotBlank String password) {}
    public record LoginRequest(@Email String email, @NotBlank String password) {}
    public record AuthResponse(String token, Long userId, String name, String email) {}
}
