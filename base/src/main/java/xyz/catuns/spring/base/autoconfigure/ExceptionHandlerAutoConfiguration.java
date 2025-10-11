package xyz.catuns.spring.base.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.catuns.spring.base.autoconfigure.properties.ExceptionHandlerProperties;
import xyz.catuns.spring.base.exception.handler.GlobalExceptionHandler;

import static xyz.catuns.spring.base.utils.Constants.EXCEPTION_CONFIG_PROPERTY_PREFIX;

/**
 * Auto-configuration for exception handling.
 * Only activates when:
 * - Spring Web is on the classpath
 * - Application is a web application
 * - Property is enabled (default: true)
 */
@AutoConfiguration
@ConditionalOnClass({RestControllerAdvice.class, ProblemDetail.class})  // Only if Spring Web present
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)  // Only for servlet web apps
@ConditionalOnProperty(
        prefix = EXCEPTION_CONFIG_PROPERTY_PREFIX,
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
@EnableConfigurationProperties(ExceptionHandlerProperties.class)
@Slf4j
public class ExceptionHandlerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public GlobalExceptionHandler globalExceptionHandler(ExceptionHandlerProperties properties) {
        log.info("Registering Global Exception Handler");
        return new GlobalExceptionHandler(properties);
    }
}
