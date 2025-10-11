package xyz.catuns.spring.jwt.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import xyz.catuns.spring.jwt.config.JwtSecurityConfigurationSelector;
import xyz.catuns.spring.jwt.autoconfigure.properties.JwtAuthenticationProperties;
import xyz.catuns.spring.jwt.core.provider.UsernamePwdAuthenticationProvider;
import xyz.catuns.spring.jwt.domain.repository.UserEntityRepository;
import xyz.catuns.spring.jwt.domain.service.UserEntityService;

/**
 * JWT Authentication Auto-Configuration with domain support
 */
@AutoConfiguration
@EnableConfigurationProperties(JwtAuthenticationProperties.class)
@ConditionalOnClass(AuthenticationManager.class)
@ConditionalOnProperty(prefix = "jwt.auth", name = "enabled", havingValue = "true", matchIfMissing = true)
public class JwtAuthenticationAutoConfiguration {

    @Autowired(required = false)
    private ApplicationContext applicationContext;

    /**
     * Default AuthenticationManager
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(AuthenticationConfiguration.class)
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * UserEntityService bean when domain is configured
     *
     * This uses the concrete UserEntityRepository from the user's domain
     */
    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    @ConditionalOnBean({UserEntityRepository.class, JwtSecurityConfigurationSelector.DomainConfiguration.class})
    public UserEntityService<?> userEntityService(
            UserEntityRepository<?> userEntityRepository,
            JwtSecurityConfigurationSelector.DomainConfiguration domainConfig) {

        Class<?> userEntityClass = domainConfig.getUserEntityClass();

        if (userEntityClass == Object.class) {
            throw new IllegalStateException(
                    "UserEntity class must be specified in @EnableJwtSecurity when using UserEntityService"
            );
        }

        return new UserEntityService<>(userEntityRepository);
    }

    /**
     * Username/Password Authentication Provider
     */
    @Bean
    @ConditionalOnMissingBean(AuthenticationProvider.class)
    @ConditionalOnBean({UserEntityService.class, PasswordEncoder.class})
    public AuthenticationProvider usernamePasswordAuthenticationProvider(
            UserEntityService<?> userDetailsService,
            PasswordEncoder passwordEncoder) {
        return new UsernamePwdAuthenticationProvider(userDetailsService, passwordEncoder);
    }

    /**
     * Default PasswordEncoder
     */
    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
