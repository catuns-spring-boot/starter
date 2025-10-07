package xyz.catuns.spring.jwt.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import xyz.catuns.spring.jwt.security.customizer.*;
import xyz.catuns.spring.jwt.security.filter.FilterChainExceptionHandler;
import xyz.catuns.spring.jwt.security.jwt.JwtService;
import xyz.catuns.spring.jwt.security.jwt.filter.JwtTokenGeneratorFilter;
import xyz.catuns.spring.jwt.security.jwt.filter.JwtTokenValidatorFilter;

/**
 * Pair with {@link SecurityFilterChainCustomizers} customizers
 */
public interface JwtSecurityFilterChain {

    Customizer<HttpSecurity> httpSecurityCustomizer();

    /**
     *
     * @param http {@link HttpSecurity}
     * @param service {@link JwtService} For generate and validate filters
     * @param resolver {@link HandlerExceptionResolver} handlerExceptionResolver

     * @return {@link SecurityFilterChain} jwtSecurityFilterChain bean
     * @throws Exception on {@link HttpSecurity#build()}
     */
    @Bean(name = "jwtSecurityFilterChain")
    default SecurityFilterChain jwtSecurityFilterChain(
            HttpSecurity http,
            JwtService service,
            @Qualifier("handlerExceptionResolver")
            HandlerExceptionResolver resolver,
            CorsCustomizer corsCustomizer,
            HttpBasicCustomizer httpBasicCustomizer,
            ExceptionHandlingCustomizer exceptionHandlingCustomizer,
            AuthorizeRequestCustomizer authorizeRequestCustomizer,
            CsrfCustomizer csrfCustomizer
    ) throws Exception {

        http.cors(corsCustomizer);
        http.httpBasic(httpBasicCustomizer);
        http.exceptionHandling(exceptionHandlingCustomizer);
        http.authorizeHttpRequests(authorizeRequestCustomizer);
        http.csrf(csrfCustomizer);
        http.sessionManagement(new StatelessSessionManagementCustomizer());
        this.httpSecurityCustomizer().customize(http);
        this.applyFilters(http, service, resolver);
        return http.build();
    }


    /**
     * Adds {@link JwtTokenValidatorFilter} (before) {@link BasicAuthenticationFilter}<br>
     * Adds {@link JwtTokenGeneratorFilter} (after) {@link BasicAuthenticationFilter}<br>
     * Adds {@link FilterChainExceptionHandler} (before) {@link LogoutFilter}<br>
     * @param http {@link HttpSecurity}
     * @param jwtService {@link JwtService}
     * @param resolver {@link HandlerExceptionResolver}
     */
    default void applyFilters(
            HttpSecurity http,
            JwtService jwtService,
            HandlerExceptionResolver resolver
    ) {
        http.addFilterBefore(new JwtTokenValidatorFilter(jwtService), BasicAuthenticationFilter.class);
        http.addFilterAfter(new JwtTokenGeneratorFilter(jwtService), BasicAuthenticationFilter.class);
        http.addFilterBefore(new FilterChainExceptionHandler(resolver), LogoutFilter.class);
    }
}
