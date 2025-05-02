package xyz.catuns.spring.base.controller.request;

public record UserLogin(
        String email,
        String password
) {
}
