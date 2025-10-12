package xyz.catuns.spring.jwt.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.HandlerExceptionResolver;
import xyz.catuns.spring.jwt.autoconfigure.properties.JwtSecurityProperties;
import xyz.catuns.spring.jwt.config.OrderedSecurityFilterChain;
import xyz.catuns.spring.jwt.core.service.JwtService;
import xyz.catuns.spring.jwt.exception.handler.JwtAccessDeniedHandler;
import xyz.catuns.spring.jwt.exception.handler.JwtAuthenticationEntryPoint;
import xyz.catuns.spring.jwt.security.configurer.JwtExceptionHandlingConfigurer;
import xyz.catuns.spring.jwt.security.configurer.JwtSecurityConfigurer;
import xyz.catuns.spring.jwt.security.configurer.JwtFilterConfigurer;

import java.util.Arrays;
import java.util.List;

/**
 * JWT Security Auto-Configuration
 *
 * Conditionally configures:
 * - Security filter chain
 * - Filter configurer
 * - Exception handling
 * - CORS
 *
 * Only applies when:
 * - This is a web application
 * - Spring Security is on classpath
 * - JWT security is enabled via properties
 * - JwtService bean exists
 */
@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(JwtSecurityProperties.class)
@ConditionalOnWebApplication
@ConditionalOnClass(HttpSecurity.class)
@ConditionalOnBean(JwtService.class)
@ConditionalOnProperty(prefix = "jwt.security", name = "enabled", havingValue = "true", matchIfMissing = true)
public class JwtSecurityAutoConfiguration {

    private final JwtSecurityProperties properties;

    public JwtSecurityAutoConfiguration(JwtSecurityProperties properties) {
        this.properties = properties;
        log.debug("init JwtSecurityAutoConfiguration {}", properties);
    }

    /**
     * Default JWT authentication entry point
     * Handles 401 Unauthorized responses
     */
    @Bean
    @ConditionalOnMissingBean(name = "jwtAuthenticationEntryPoint")
    public AuthenticationEntryPoint jwtAuthenticationEntryPoint(ObjectMapper objectMapper) {
        log.debug("creating AuthenticationEntryPoint");
        return new JwtAuthenticationEntryPoint(objectMapper, properties);
    }

    /**
     * Default JWT access denied handler
     * Handles 403 Forbidden responses
     */
    @Bean
    @ConditionalOnMissingBean(name = "jwtAccessDeniedHandler")
    public AccessDeniedHandler jwtAccessDeniedHandler(ObjectMapper objectMapper) {
        log.debug("creating AccessDeniedHandler");
        return new JwtAccessDeniedHandler(objectMapper, properties);
    }

    /**
     * Default CORS configuration source
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "jwt.security.cors", name = "enabled", havingValue = "true", matchIfMissing = true)
    public CorsConfigurationSource corsConfigurationSource(JwtSecurityProperties properties) {
        if (!properties.getCors().isEnabled()) {
            return request -> null;
        }
        log.debug("creating CorsConfigurationSource");
        CorsConfiguration config = new CorsConfiguration();

        JwtSecurityProperties.CorsConfig cors = properties.getCors();

        if (cors.getAllowedOrigins().length > 0) {
            config.setAllowedOrigins(Arrays.asList(cors.getAllowedOrigins()));
        } else {
            config.setAllowedOriginPatterns(List.of("*"));
        }

        config.setAllowedMethods(Arrays.asList(cors.getAllowedMethods()));

        if (cors.getAllowedHeaders().length > 0) {
            config.setAllowedHeaders(Arrays.asList(cors.getAllowedHeaders()));
        } else {
            config.setAllowedHeaders(List.of("*"));
        }

        config.setAllowCredentials(cors.isAllowCredentials());
        config.setMaxAge(cors.getMaxAge());

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    /**
     * Default JWT Filter configurer
     */
    @Bean
    @ConditionalOnMissingBean(JwtFilterConfigurer.class)
    public JwtFilterConfigurer filterConfigurer(
            JwtService<Authentication> jwtService,
            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver
    ) {
        log.debug("creating JwtFilterConfigurer");
        return new JwtFilterConfigurer(jwtService)
                .validatorHeaderName(properties.getValidation().getHeaderName())
                .validatorTokenPrefix(properties.getValidation().getTokenPrefix())
                .generatorTokenHeader(properties.getGeneration().getHeaderName())
                .generatorTokenPrefix(properties.getGeneration().getTokenPrefix())
                .generatorExpirationHeader(properties.getGeneration().getExpirationHeaderName())
                .exceptionResolver(resolver);
    }

    /**
     * Default JWT exception handling configurer
     */
    @Bean
    @ConditionalOnMissingBean(JwtExceptionHandlingConfigurer.class)
    public JwtExceptionHandlingConfigurer JwtExceptionHandlingConfigurer(
            AccessDeniedHandler jwtAccessDeniedHandler,
            AuthenticationEntryPoint jwtAuthenticationEntryPoint
    ) {
        log.debug("creating JwtExceptionHandlingConfigurer");
        return new JwtExceptionHandlingConfigurer()
                .properties(properties)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint);
    }
    /**
     * Default JWT Security Filter Chain
     */
    @Bean
    @ConditionalOnMissingBean(name = "jwtSecurityFilterChain")
    @ConditionalOnProperty(prefix = "jwt.security.filter", name = "enabled", havingValue = "true", matchIfMissing = true)
    public SecurityFilterChain jwtSecurityFilterChain(
            HttpSecurity http,
            JwtFilterConfigurer filterConfigurer,
            JwtExceptionHandlingConfigurer exceptionConfigurer,
            CorsConfigurationSource corsConfigurationSource
    ) throws Exception {

        log.debug("creating SecurityFilterChain");
        // Apply JWT configurer
        http.with(JwtSecurityConfigurer.jwt(), jwt -> {
            jwt.exceptionConfigurer(() -> exceptionConfigurer);
            jwt.filterConfigurer(() -> filterConfigurer);

            if (!properties.getFilter().isValidator()) {
                jwt.disableValidator();
            }
            if (!properties.getFilter().isGenerator()) {
                jwt.disableGenerator();
            }
            if (!properties.getFilter().isExceptionHandler()) {
                jwt.disableExceptionHandler();
            }
        });


        if (properties.getCors().isEnabled()) {
            http.cors(cors -> cors
                    .configurationSource(corsConfigurationSource));
        } else {
            http.cors(AbstractHttpConfigurer::disable);
        }

        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(auth -> {
            String[] publicPaths = properties.getPublicPaths();
            if (publicPaths.length > 0) {
                auth.requestMatchers(publicPaths).permitAll();
            }
            auth.anyRequest().authenticated();
        });
        DefaultSecurityFilterChain chain = http.build();
        int order = properties.getFilter().getOrder();
        return new OrderedSecurityFilterChain(chain, order);
    }


}
