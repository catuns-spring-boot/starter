package xyz.catuns.spring.jwt.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import xyz.catuns.spring.jwt.autoconfigure.properties.JwtProperties;
import xyz.catuns.spring.jwt.core.service.AuthJwtService;
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
@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtAutoConfiguration {
    private final JwtProperties properties;

    public JwtAutoConfiguration(JwtProperties properties) {
        this.properties = properties;
        log.debug("init JwtAutoConfiguration {}", properties);
    }

    /**
     * Default JWT Service for Authentication
     * Only created if JWT functionality is enabled
     */
    @Bean
    @ConditionalOnMissingBean(AuthJwtService.class)
    @ConditionalOnProperty(prefix = "jwt", name = "enabled", havingValue = "true", matchIfMissing = true)
    public AuthJwtService defaultAuthJwtService() throws MissingSecretException {
        log.debug("creating AuthJwtService");
        return new AuthJwtService(properties);
    }
}
