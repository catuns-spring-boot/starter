package xyz.catuns.spring.jwt.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;

public record UserUpdate(
        @Schema(description = "username - at least 4 characters", example = "nacho_supreme16")
        @Min(value = 4, message = "username must have 4 characters")
        String username,

        @Schema(description = "email - must be unique", example = "contact@catuns.xyz")
        @Email
        String email

// Todo: create a separate method for password updates
//        @Schema(description = "password - at least 8 characters", example = "thisisabadpassword")
//        @Min(value = 8, message = "password must have more than 8 characters")
//        String password
) {
}
