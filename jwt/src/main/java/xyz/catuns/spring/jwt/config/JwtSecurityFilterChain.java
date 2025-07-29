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

public interface JwtSecurityFilterChain {

    Customizer<HttpSecurity> httpSecurityCustomizer();

    /**
     * Customizer default configuration {@link SecurityFilterChainCustomizers}
     *
     * @param http {@link HttpSecurity}
     * @param service {@link JwtService} Generate and validate tokens
     *         - {@link JwtTokenGeneratorFilter}
     *         - {@link JwtTokenValidatorFilter}
     * @param resolver Exception handling

     * @return {@link SecurityFilterChain} bean
     * @throws Exception build method
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

        this.httpSecurityCustomizer().customize(http);
        applyRequired(http, service, resolver);
        return http.build();
    }


    static void applyRequired(
            HttpSecurity http,
            JwtService jwtService,
            HandlerExceptionResolver resolver
    ) throws Exception {
        http.sessionManagement(new StatelessSessionManagementCustomizer());
        http.addFilterBefore(new JwtTokenValidatorFilter(jwtService), BasicAuthenticationFilter.class);
        http.addFilterAfter(new JwtTokenGeneratorFilter(jwtService), BasicAuthenticationFilter.class);
        http.addFilterBefore(new FilterChainExceptionHandler(resolver), LogoutFilter.class);
    }
}
