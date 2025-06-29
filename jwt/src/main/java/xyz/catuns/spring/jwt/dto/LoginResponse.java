package xyz.catuns.spring.jwt.dto;

import xyz.catuns.spring.jwt.security.jwt.JwtToken;

import java.io.Serializable;
import java.util.Date;

public record LoginResponse(
        String token,
        Date expiration,
        String email,
        String roles
) implements Serializable {}
