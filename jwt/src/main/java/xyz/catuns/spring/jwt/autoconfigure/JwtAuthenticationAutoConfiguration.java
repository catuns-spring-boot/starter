package xyz.catuns.spring.jwt.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
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
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import xyz.catuns.spring.jwt.config.DomainMetadata;
import xyz.catuns.spring.jwt.autoconfigure.properties.JwtAuthenticationProperties;
import xyz.catuns.spring.jwt.core.provider.UsernamePwdAuthenticationProvider;
import xyz.catuns.spring.jwt.domain.repository.UserEntityRepository;
import xyz.catuns.spring.jwt.domain.service.UserEntityService;

import java.util.ArrayList;
import java.util.List;

/**
 * JWT Authentication Auto-Configuration with domain support
 */
@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(JwtAuthenticationProperties.class)
@ConditionalOnClass(AuthenticationManager.class)
@ConditionalOnProperty(prefix = "jwt.auth", name = "enabled", havingValue = "true", matchIfMissing = true)
public class JwtAuthenticationAutoConfiguration {

    @Autowired(required = false)
    private ApplicationContext applicationContext;

    public JwtAuthenticationAutoConfiguration(JwtAuthenticationProperties  properties) {
        log.debug("init JwtAuthenticationAutoConfiguration {}", properties);
    }

    /**
     * Default AuthenticationManager
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(AuthenticationConfiguration.class)
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {
       return configuration.getAuthenticationManager();
    }

    /**
     * UserEntityService bean when domain is configured
     *
     * This uses the concrete UserEntityRepository from the user's domain
     */
    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    @ConditionalOnBean({UserEntityRepository.class, DomainMetadata.class})
    @ConditionalOnProperty(
            prefix = "jwt.auth",
            name = "use-entity-service",
            havingValue = "true",
            matchIfMissing = true
    )
    public UserEntityService<?> userEntityService(
            UserEntityRepository<?> userEntityRepository,
            DomainMetadata domainMetadata
    ) {
        Class<?> domainClazz = domainMetadata.getUserRepositoryClass();
        if (domainClazz == Object.class) {
            throw new IllegalStateException(
                    "UserEntityRepository class must be specified in @EnableJwtSecurity when using UserEntityService"
            );
        }

        Class<?> beanClazz = userEntityRepository.getClass();
        if (!domainClazz.isAssignableFrom(beanClazz)) {
            throw new IllegalStateException(
                    "UserRepository class must assignable to %s as specified in @EnableJwtSecurity"
                            .formatted(domainClazz.getName())
            );
        }

        log.info("Creating UserEntityService");
        return new UserEntityService<>(userEntityRepository);
    }

    /**
     * Username/Password Authentication Provider
     */
    @Bean
    @ConditionalOnMissingBean(AuthenticationProvider.class)
    @ConditionalOnBean({UserEntityService.class})
    public AuthenticationProvider usernamePasswordAuthenticationProvider(
            UserEntityService<?> userDetailsService,
            PasswordEncoder passwordEncoder) {
        log.debug("creating UsernamePwdAuthenticationProvider");
        return new UsernamePwdAuthenticationProvider(userDetailsService, passwordEncoder);
    }

    /**
     * Default PasswordEncoder
     */
    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
        log.debug("creating PasswordEncoder");
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
