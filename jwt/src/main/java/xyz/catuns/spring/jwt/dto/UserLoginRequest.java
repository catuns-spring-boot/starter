package xyz.catuns.spring.jwt.dto;

import xyz.catuns.spring.jwt.controller.request.LoginRequest;

public record UserLoginRequest(String username, String password) implements LoginRequest {
}
