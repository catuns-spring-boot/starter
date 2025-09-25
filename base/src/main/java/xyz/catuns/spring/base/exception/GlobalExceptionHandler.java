package xyz.catuns.spring.base.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import xyz.catuns.spring.base.exception.controller.ControllerException;

@ControllerAdvice
public class GlobalExceptionHandler {

    protected static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> genericExceptionHandler(
            Exception e,
            HttpServletRequest request
    ) {
        String message = String.format("[%s]: %s", e.getClass().getSimpleName(), e.getMessage());
        ErrorMessage errorMessage = new ErrorMessage(
                request.getRequestURI(),
                message
        );
        log.debug("{} {}", request.getMethod(), errorMessage);

        return ResponseEntity.status(errorMessage.getStatusCode())
                .body(errorMessage);
    }

    @ExceptionHandler(ControllerException.class)
    public ResponseEntity<ErrorMessage> controllerExceptionHandler(
            ControllerException e,
            HttpServletRequest request
    ) {
        ErrorMessage errorMessage = new ErrorMessage(
                request.getRequestURI(),
                e.getMessage(),
                e.getHttpStatus()
        );

        return ResponseEntity.status(errorMessage.getStatusCode())
                .body(errorMessage);
    }
}
