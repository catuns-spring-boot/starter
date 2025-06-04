package xyz.catuns.spring.jwt.controller.request;

public record UserLogin(
        String email,
        String password
) {
}
