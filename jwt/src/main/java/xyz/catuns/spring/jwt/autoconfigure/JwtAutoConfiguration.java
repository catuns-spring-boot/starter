package xyz.catuns.spring.jwt.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import xyz.catuns.spring.jwt.autoconfigure.properties.JwtProperties;
import xyz.catuns.spring.jwt.core.service.AuthJwtService;
import xyz.catuns.spring.jwt.core.service.JwtService;
import xyz.catuns.spring.jwt.exception.MissingSecretException;

@AutoConfiguration
@EnableConfigurationProperties(JwtProperties.class)
@Import({JwtSecurityAutoConfiguration.class})
public class JwtAutoConfiguration
{
    private final JwtProperties properties;

    public JwtAutoConfiguration(JwtProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtService<Authentication> authJwtService() throws MissingSecretException {
        return new AuthJwtService(properties);
    }
}
