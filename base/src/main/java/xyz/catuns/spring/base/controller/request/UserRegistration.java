package xyz.catuns.spring.base.controller.request;

public record UserRegistration(
        String username,
        String email,
        String password
) {
}
