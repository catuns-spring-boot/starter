package xyz.catuns.spring.constraint.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "constraint-violation")
public class ConstraintViolationProperties {

    /**
     * Enable or disable the constraint violation handler
     */
    private boolean enabled = true;

    /**
     * Expose technical details in problem detail responses (for development)
     */
    private boolean exposeTechnicalDetails = false;

    /**
     * Include rejected values in the response
     */
    private boolean includeRejectedValues = false;

    /**
     * HTTP status code to use for constraint violations (default: 409 CONFLICT)
     */
    private int statusCode = 409;

    /**
     * Custom field name transformation strategy
     */
    private FieldNameStrategy fieldNameStrategy = FieldNameStrategy.TITLE_CASE;

    public enum FieldNameStrategy {
        /**
         * Convert snake_case to Title Case (e.g., user_email -> User Email)
         */
        TITLE_CASE,

        /**
         * Convert snake_case to camelCase (e.g., user_email -> userEmail)
         */
        CAMEL_CASE,

        /**
         * Keep original field name as-is
         */
        ORIGINAL
    }

}