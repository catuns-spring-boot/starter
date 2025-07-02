package xyz.catuns.spring.jwt.dto;

import xyz.catuns.spring.jwt.controller.request.RegisterRequest;

public record UserRegister(
        String username,
        String email,
        String password
) implements RegisterRequest {}
