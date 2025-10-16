package xyz.catuns.spring.jwt.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class TokenInvalidException extends BadCredentialsException {

    public TokenInvalidException(Exception exception) {
        super("Invalid token received", exception);
    }
}
