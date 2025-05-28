package xyz.catuns.spring.base.exception;

import org.springframework.http.HttpStatus;

public class ControllerException extends RuntimeException {

    protected final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ControllerException(String message) {
        super(message);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
