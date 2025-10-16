package xyz.catuns.spring.jwt.autoconfigure.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Jwt Authentication manager properties
 *
 * @author Devin Catuns
 * @since 0.0.6
 */
@Data
@ConfigurationProperties(prefix = "jwt.auth")
public class JwtAuthenticationProperties {

    /**
     * Enables authentication manager
     *
     */
    private boolean enabled = true;
    /**
     * Enable use of UserEntityService
     */
    private boolean useEntityService = true;
}
