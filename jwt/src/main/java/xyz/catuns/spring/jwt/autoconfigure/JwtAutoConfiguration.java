package xyz.catuns.spring.jwt.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import xyz.catuns.spring.jwt.autoconfigure.properties.JwtProperties;
import xyz.catuns.spring.jwt.core.service.AuthJwtService;
import xyz.catuns.spring.jwt.core.service.JwtService;
import xyz.catuns.spring.jwt.exception.MissingSecretException;

/**
 * <h1>JWT Auto-Configuration</h1>
 *
 * <p>
 * Conditionally imports security configuration based on:
 * <ul>
 *      <li>Web application presence</li>
 *      <li>Spring Security on classpath</li>
 *      <li>Property settings</li>
 * </ul>
 * </p>
 */
@AutoConfiguration
@EnableConfigurationProperties(JwtProperties.class)
@Import({JwtSecurityAutoConfiguration.class,
        JwtAuthenticationAutoConfiguration.class})
public class JwtAutoConfiguration {
    private final JwtProperties properties;

    public JwtAutoConfiguration(JwtProperties properties) {
        this.properties = properties;
    }

    /**
     * Default JWT Service for Authentication
     * Only created if JWT functionality is enabled
     */
    @Bean
    @ConditionalOnMissingBean(JwtService.class)
    @ConditionalOnProperty(prefix = "jwt", name = "enabled", havingValue = "true", matchIfMissing = true)
    public JwtService<Authentication> authJwtService() throws MissingSecretException {
        return new AuthJwtService(properties);
    }
}
