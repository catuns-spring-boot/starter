package xyz.catuns.spring.jwt.autoconfigure.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "jwt.auth")
public class JwtAuthenticationProperties {

    private boolean enabled = true;
    private boolean useEntityService = true;
}
