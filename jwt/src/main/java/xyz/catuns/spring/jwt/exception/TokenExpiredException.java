package xyz.catuns.spring.jwt.exception;

public class TokenExpiredException extends AuthorizationException {

    public TokenExpiredException() {
        super("token expired");

    }
}
