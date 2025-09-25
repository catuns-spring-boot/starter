package xyz.catuns.spring.jwt.exception;

public class TokenRefreshException extends AuthorizationException{
    public TokenRefreshException(String message) {
        super(message);
    }
}
