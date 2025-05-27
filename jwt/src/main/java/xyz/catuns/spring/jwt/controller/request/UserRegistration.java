package xyz.catuns.spring.jwt.controller.request;

public record UserRegistration(
        String username,
        String email,
        String password
) {
}
