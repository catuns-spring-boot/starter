package xyz.catuns.spring.jwt.exception;

import org.springframework.http.HttpStatus;
import xyz.catuns.spring.base.exception.ControllerException;

public class AuthorizationException extends ControllerException {

    public AuthorizationException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
