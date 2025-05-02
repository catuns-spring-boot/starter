package xyz.catuns.spring.base.dto;

import java.io.Serializable;

public record LoginResponse(
        String accessToken,
        String email,
        String roles
) implements Serializable {}
