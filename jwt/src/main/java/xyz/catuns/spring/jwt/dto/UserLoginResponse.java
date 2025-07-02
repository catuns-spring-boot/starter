package xyz.catuns.spring.jwt.dto;

import xyz.catuns.spring.jwt.controller.response.LoginResponse;

import java.util.Date;
import java.util.Set;

public record UserLoginResponse(
        String token,
        Date expiration,
        String email,
        Set<String> roles
) implements LoginResponse {}
