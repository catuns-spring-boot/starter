package xyz.catuns.spring.base.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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

    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        final Map<String, Object> map = new HashMap<>();
        map.put("path", path);
        map.put("message", message);
        map.put("statusCode", statusCode);
        map.put("timestamp", timestamp);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper.writeValueAsString(map);
    }

}
