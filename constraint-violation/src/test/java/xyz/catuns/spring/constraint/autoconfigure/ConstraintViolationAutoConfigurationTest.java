package xyz.catuns.spring.constraint.autoconfigure;


import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import xyz.catuns.spring.constraint.handler.GlobalDataIntegrityExceptionHandler;
import xyz.catuns.spring.constraint.parser.ConstraintViolationParser;
import xyz.catuns.spring.constraint.strategy.postgresql.PostgreSQLUniqueStrategy;

import static org.assertj.core.api.Assertions.assertThat;

class ConstraintViolationAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(ConstraintViolationAutoConfiguration.class));

    @Test
    void shouldAutoConfigureWhenEnabled() {
        this.contextRunner
                .withPropertyValues("constraint-violation.enabled=true")
                .run(context -> {
                    assertThat(context).hasSingleBean(ConstraintViolationParser.class);
                    assertThat(context).hasSingleBean(GlobalDataIntegrityExceptionHandler.class);
                });
    }

    @Test
    void shouldNotAutoConfigureWhenDisabled() {
        this.contextRunner
                .withPropertyValues("constraint-violation.enabled=false")
                .run(context -> {
                    assertThat(context).doesNotHaveBean(ConstraintViolationParser.class);
                    assertThat(context).doesNotHaveBean(GlobalDataIntegrityExceptionHandler.class);
                });
    }

    @Test
    void shouldRegisterPostgreSQLStrategiesWhenDriverPresent() {
        this.contextRunner
                .withPropertyValues("constraint-violation.enabled=true")
                .run(context -> {
                    // This test will only pass if PostgreSQL driver is on classpath
                    if (isClassPresent("org.postgresql.Driver")) {
                        assertThat(context).hasSingleBean(PostgreSQLUniqueStrategy.class);
                    }
                });
    }

    @Test
    void shouldRespectCustomProperties() {
        this.contextRunner
                .withPropertyValues(
                        "constraint-violation.enabled=true",
                        "constraint-violation.expose-technical-details=true",
                        "constraint-violation.status-code=400"
                )
                .run(context -> {
                    ConstraintViolationProperties properties =
                            context.getBean(ConstraintViolationProperties.class);

                    assertThat(properties.isExposeTechnicalDetails()).isTrue();
                    assertThat(properties.getStatusCode()).isEqualTo(400);
                });
    }

    private boolean isClassPresent(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}