package xyz.catuns.spring.jwt.exception;

public class MissingSecretException extends Exception {
    public MissingSecretException() {
        super("Must define JWT secret");
    }
}
