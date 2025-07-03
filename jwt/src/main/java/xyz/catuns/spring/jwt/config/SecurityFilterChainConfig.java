package xyz.catuns.spring.jwt.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import xyz.catuns.spring.base.exception.GlobalAccessDeniedHandler;
import xyz.catuns.spring.base.exception.GlobalAuthenticationEntryPoint;
import xyz.catuns.spring.jwt.security.cors.DefaultCorsConfigurationHandler;
import xyz.catuns.spring.jwt.security.customizer.*;
import xyz.catuns.spring.jwt.security.filter.FilterChainExceptionHandler;
import xyz.catuns.spring.jwt.security.jwt.JwtService;
import xyz.catuns.spring.jwt.security.jwt.filter.JwtTokenGeneratorFilter;
import xyz.catuns.spring.jwt.security.jwt.filter.JwtTokenValidatorFilter;

@Configuration
public class SecurityFilterChainConfig {

    private final JwtService jwtService;
    private final HandlerExceptionResolver resolver;

    public SecurityFilterChainConfig(
            JwtService jwtService,
            @Qualifier("handlerExceptionResolver")
            HandlerExceptionResolver resolver
    ) {
        this.jwtService = jwtService;
        this.resolver = resolver;
    }


    @Bean
    @ConditionalOnMissingBean
    CorsCustomizer corsCustomizer() {
        return (configurer) -> configurer
                .configurationSource(new DefaultCorsConfigurationHandler());
    }

    @Bean
    @ConditionalOnMissingBean
    HttpBasicCustomizer httpBasicCustomizer() {
        return (configurer) -> configurer
                .authenticationEntryPoint(new GlobalAuthenticationEntryPoint());

    }

    @Bean
    @ConditionalOnMissingBean
    ExceptionHandlingCustomizer exceptionHandlingCustomizer() {
        return handler -> handler
                .authenticationEntryPoint(new GlobalAuthenticationEntryPoint())
                .accessDeniedHandler(new GlobalAccessDeniedHandler());
    }

    @Bean
    @ConditionalOnMissingBean
    CsrfCustomizer csrfCustomizer() {
        return AbstractHttpConfigurer::disable;
    }

    @Bean
    @ConditionalOnMissingBean
    AuthorizeRequestCustomizer authorizeRequestCustomizer() {
        return registry -> registry
                .requestMatchers(
                        "/swagger-ui/**",
                        "/swagger/index.html",
                        "/v3/api/docs/**",
                        "/error",
                        "/auth/login",
                        "/auth/register")
                .permitAll()
                .anyRequest().authenticated();
    }

}
