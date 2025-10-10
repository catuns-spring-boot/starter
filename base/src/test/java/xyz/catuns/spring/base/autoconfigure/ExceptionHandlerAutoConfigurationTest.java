package xyz.catuns.spring.base.autoconfigure;


import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.context.FilteredClassLoader;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.catuns.spring.base.exception.handler.GlobalExceptionHandler;

import static org.assertj.core.api.Assertions.assertThat;
import static xyz.catuns.spring.base.utils.Constants.EXCEPTION_CONFIG_PROPERTY_PREFIX;

class ExceptionHandlerAutoConfigurationTest {

    private final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(
                    ExceptionHandlerAutoConfiguration.class,
                    WebMvcAutoConfiguration.class
            ));

    @Test
    void shouldAutoConfigureWhenWebPresent() {
        contextRunner.run(context -> {
            assertThat(context).hasSingleBean(GlobalExceptionHandler.class);
            assertThat(context).hasSingleBean(ExceptionHandlerProperties.class);
        });
    }

    @Test
    void shouldNotAutoConfigureWhenWebMissing() {
        contextRunner
                .withClassLoader(new FilteredClassLoader(RestControllerAdvice.class, ProblemDetail.class))
                .run(context -> {
                    assertThat(context).doesNotHaveBean(GlobalExceptionHandler.class);
                });
    }

    @Test
    void shouldNotAutoConfigureWhenDisabled() {
        contextRunner
                .withPropertyValues(EXCEPTION_CONFIG_PROPERTY_PREFIX + ".enabled=false")
                .run(context -> {
                    assertThat(context).doesNotHaveBean(GlobalExceptionHandler.class);
                });
    }

    @Test
    void shouldRespectCustomProperties() {
        contextRunner
                .withPropertyValues(
                        EXCEPTION_CONFIG_PROPERTY_PREFIX + ".enabled=true",
                        EXCEPTION_CONFIG_PROPERTY_PREFIX + ".include-stack-trace=true",
                        EXCEPTION_CONFIG_PROPERTY_PREFIX + ".log-exceptions=false"
                )
                .run(context -> {
                    ExceptionHandlerProperties properties = context.getBean(ExceptionHandlerProperties.class);

                    assertThat(properties.isEnabled()).isTrue();
                    assertThat(properties.isIncludeStackTrace()).isTrue();
                    assertThat(properties.isLogExceptions()).isFalse();
                });
    }

    @Test
    void shouldAllowOverride() {
        contextRunner
                .withUserConfiguration(CustomExceptionHandlerConfig.class)
                .run(context -> {
                    // Should use custom handler instead
                    assertThat(context.getBeansOfType(GlobalExceptionHandler.class)).hasSize(1);
                });
    }

    @TestConfiguration
    static class CustomExceptionHandlerConfig {
        @Bean
        public GlobalExceptionHandler customGlobalExceptionHandler(ExceptionHandlerProperties properties) {
            return new GlobalExceptionHandler(properties) {
                // Custom implementation
            };
        }
    }
}