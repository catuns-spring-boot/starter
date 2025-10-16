package xyz.catuns.spring.jwt.exception;

import io.jsonwebtoken.ExpiredJwtException;
import xyz.catuns.spring.base.exception.controller.UnauthorizedException;

public class TokenExpiredException extends UnauthorizedException {

    public TokenExpiredException() {
        super("token expired");

    }

    public TokenExpiredException(ExpiredJwtException exception) {
        this();
    }
}
