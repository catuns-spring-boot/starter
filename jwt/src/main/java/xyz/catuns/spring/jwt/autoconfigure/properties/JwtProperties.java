package xyz.catuns.spring.jwt.autoconfigure.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import xyz.catuns.spring.jwt.core.service.JwtService;

import java.time.Duration;

import static xyz.catuns.spring.jwt.utils.Constants.Config.JWT_CONFIG_PROPERTY_PREFIX;

/**
 * Properties for defining Jwt Service
 *
 * @author Devin Catuns
 * @since 0.0.6
 */
@Data
@ConfigurationProperties(prefix = JWT_CONFIG_PROPERTY_PREFIX)
public class JwtProperties {

    /**
     * Enable auto configuration
     */
    private boolean enabled = true;
    /**
     * Issuer of the token
     */
    private String issuer;
    /**
     * Jwt secret key
     */
    private String secret;
    /**
     * Expiration duration for refresh tokens
     */
    private Duration refresh = Duration.ofDays(1);
    /**
     * Expiration duration of auth tokens
     */
    private Duration expiration = Duration.ofHours(10);

}
