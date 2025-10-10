package xyz.catuns.spring.jwt.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import xyz.catuns.spring.base.exception.handler.GlobalAccessDeniedHandler;
import xyz.catuns.spring.base.exception.GlobalAuthenticationEntryPoint;
import xyz.catuns.spring.jwt.security.cors.DefaultCorsConfigurationHandler;
import xyz.catuns.spring.jwt.security.customizer.*;

@Configuration
public class SecurityFilterChainCustomizers {

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
        return registry -> {
            registry.requestMatchers(
                            "/swagger-ui/**",
                            "/swagger/index.html",
                            "/v3/api-docs/**",
                            "/actuator/health",
                            "/favicon.ico",
                            "/error",
                            "/auth/login",
                            "/auth/register")
                    .permitAll();
            registry.anyRequest().authenticated();
        };
    }

}
