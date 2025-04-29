package xyz.catuns.spring.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ErrorMessage {
    private final String path;
    private final String message;
    private final HttpStatus statusCode;
    private final LocalDateTime timestamp;

    public ErrorMessage(String path, String message) {
        this(path, message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ErrorMessage(String path, String message, HttpStatus statusCode) {
        this(path, message, statusCode, LocalDateTime.now());
    }

    public ErrorMessage(String path, String message, HttpStatus statusCode, LocalDateTime timestamp) {
        this.path = path;
        this.message = message;
        this.statusCode = statusCode;
        this.timestamp = timestamp;
    }

}
