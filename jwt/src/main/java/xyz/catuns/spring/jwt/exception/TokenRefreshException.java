package xyz.catuns.spring.jwt.exception;

import xyz.catuns.spring.base.exception.controller.UnauthorizedException;

public class TokenRefreshException extends UnauthorizedException {
    public TokenRefreshException(String message) {
        super(message);
    }
}
