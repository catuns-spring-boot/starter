package xyz.catuns.spring.jwt.security.configurer;


import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.HandlerExceptionResolver;
import xyz.catuns.spring.jwt.core.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import xyz.catuns.spring.jwt.core.model.JwtToken;
import xyz.catuns.spring.jwt.security.filter.JwtExceptionHandlerFilter;
import xyz.catuns.spring.jwt.security.filter.JwtTokenGeneratorFilter;
import xyz.catuns.spring.jwt.security.filter.JwtTokenValidatorFilter;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

import static xyz.catuns.spring.jwt.utils.Constants.Headers.AUTHORIZATION_KEY;
import static xyz.catuns.spring.jwt.utils.Constants.Headers.TOKEN_EXPIRATION_KEY;
import static xyz.catuns.spring.jwt.utils.Constants.Jwt.BEARER_TOKEN_PREFIX;

/**
 * Builder for configuring JWT filters
 * Used by JwtSecurityConfigurer to provide fluent API
 */
@Slf4j
public class JwtFilterConfigurer {

    @Setter
    private JwtService<Authentication> jwtService;

    // Validator config
    private String validatorHeaderName = AUTHORIZATION_KEY;
    private String validatorTokenPrefix = BEARER_TOKEN_PREFIX;
    private Predicate<HttpServletRequest> validatorPredicate;

    // Generator config
    private String generatorTokenHeader = AUTHORIZATION_KEY;
    private String generatorExpirationHeader = TOKEN_EXPIRATION_KEY;
    private String generatorTokenPrefix = BEARER_TOKEN_PREFIX;
    private Predicate<HttpServletRequest> generatorPredicate;
    private BiConsumer<HttpServletResponse, JwtToken> tokenWriter;

    // Exception handler config
    private HandlerExceptionResolver exceptionResolver;
    private boolean logExceptions = true;

    public JwtFilterConfigurer(JwtService<Authentication> jwtService) {
        this.jwtService = jwtService;
    }

    // Validator configuration

    public JwtFilterConfigurer validatorHeaderName(String headerName) {
        this.validatorHeaderName = headerName;
        return this;
    }

    public JwtFilterConfigurer validatorTokenPrefix(String tokenPrefix) {
        this.validatorTokenPrefix = tokenPrefix;
        return this;
    }

    public JwtFilterConfigurer validatorPredicate(Predicate<HttpServletRequest> predicate) {
        this.validatorPredicate = predicate;
        return this;
    }

    // Generator configuration

    public JwtFilterConfigurer generatorTokenHeader(String headerName) {
        this.generatorTokenHeader = headerName;
        return this;
    }

    public JwtFilterConfigurer generatorExpirationHeader(String headerName) {
        this.generatorExpirationHeader = headerName;
        return this;
    }

    public JwtFilterConfigurer generatorTokenPrefix(String tokenPrefix) {
        this.generatorTokenPrefix = tokenPrefix;
        return this;
    }

    public JwtFilterConfigurer generatorPredicate(Predicate<HttpServletRequest> predicate) {
        this.generatorPredicate = predicate;
        return this;
    }

    public JwtFilterConfigurer tokenWriter(BiConsumer<HttpServletResponse, JwtToken> writer) {
        this.tokenWriter = writer;
        return this;
    }

    // Exception handler configuration

    public JwtFilterConfigurer exceptionResolver(HandlerExceptionResolver resolver) {
        this.exceptionResolver = resolver;
        return this;
    }

    public JwtFilterConfigurer logExceptions(boolean logExceptions) {
        this.logExceptions = logExceptions;
        return this;
    }

    // Builder methods

    public JwtTokenValidatorFilter buildValidator() {
        JwtTokenValidatorFilter filter = new JwtTokenValidatorFilter(jwtService);
        filter.setHeaderName(validatorHeaderName);
        filter.setTokenPrefix(validatorTokenPrefix);
        if (validatorPredicate != null) {
            filter.setRequiresValidation(validatorPredicate);
        }
        return filter;
    }

    public JwtTokenGeneratorFilter buildGenerator() {
        JwtTokenGeneratorFilter filter = new JwtTokenGeneratorFilter(jwtService);
        filter.setTokenHeaderName(generatorTokenHeader);
        filter.setExpirationHeaderName(generatorExpirationHeader);
        filter.setTokenPrefix(generatorTokenPrefix);
        if (generatorPredicate != null) {
            filter.setRequiresGeneration(generatorPredicate);
        }
        if (tokenWriter != null) {
            filter.setTokenWriter(tokenWriter);
        }
        return filter;
    }

    public JwtExceptionHandlerFilter buildExceptionHandler() {
        if (exceptionResolver == null) {
            throw new IllegalStateException("HandlerExceptionResolver must be configured");
        }
        JwtExceptionHandlerFilter filter = new JwtExceptionHandlerFilter(exceptionResolver);
        filter.setLogExceptions(logExceptions);
        return filter;
    }

}