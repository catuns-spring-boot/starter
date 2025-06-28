package xyz.catuns.spring.jwt.dto;

import xyz.catuns.spring.jwt.security.jwt.JwtToken;

import java.io.Serializable;

public record LoginResponse(
        JwtToken token,
        String email,
        String roles
) implements Serializable {}
