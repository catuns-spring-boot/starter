package xyz.catuns.spring.constraint.autoconfigure;


import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import xyz.catuns.spring.constraint.handler.GlobalDataIntegrityExceptionHandler;
import xyz.catuns.spring.constraint.parser.ConstraintViolationParser;
import xyz.catuns.spring.constraint.strategy.postgresql.PostgreSQLUniqueStrategy;

import static org.assertj.core.api.Assertions.assertThat;
import static xyz.catuns.spring.constraint.Constants.CONSTRAINT_CONFIG_PROPERTY_PREFIX;

class ConstraintViolationAutoConfigurationTest {

    private final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(
                    ConstraintViolationAutoConfiguration.class  // ← This loads your auto-config
            ));

    @Test
    void debugBeans() {
        contextRunner.run(context -> {
            System.out.println("=== All Beans ===");
            for (String beanName : context.getBeanDefinitionNames()) {
                Object bean = context.getBean(beanName);
                System.out.println(beanName + " : " + bean.getClass().getName());
            }

            System.out.println("\n=== Looking for our beans ===");
            System.out.println("ConstraintViolationParser: " + context.containsBean("constraintViolationParser"));
            System.out.println("ConstraintViolationProperties: " + context.getBeansOfType(ConstraintViolationProperties.class).size());
            System.out.println("GenericStrategy: " + context.containsBean("genericStrategy"));
            System.out.println("GlobalDataIntegrityExceptionHandler: " + context.containsBean("globalDataIntegrityExceptionHandler"));

            // Check if auto-configuration was loaded
            System.out.println("\n=== Auto-Configuration Loaded? ===");
            System.out.println(context.getBeansOfType(ConstraintViolationAutoConfiguration.class));
        });
    }

    @Test
    void shouldAutoConfigureWhenEnabled() {
        this.contextRunner
                .withPropertyValues(CONSTRAINT_CONFIG_PROPERTY_PREFIX + ".enabled=true")
                .run(context -> {
                    assertThat(context).hasSingleBean(ConstraintViolationParser.class);
                    assertThat(context).hasSingleBean(GlobalDataIntegrityExceptionHandler.class);
                });
    }

    @Test
    void shouldNotAutoConfigureWhenDisabled() {
        this.contextRunner
                .withPropertyValues(CONSTRAINT_CONFIG_PROPERTY_PREFIX + ".enabled=false")
                .run(context -> {
                    assertThat(context).doesNotHaveBean(ConstraintViolationParser.class);
                    assertThat(context).doesNotHaveBean(GlobalDataIntegrityExceptionHandler.class);
                });
    }

    @Test
    void shouldRegisterPostgreSQLStrategiesWhenDriverPresent() {
        this.contextRunner
                .withPropertyValues(CONSTRAINT_CONFIG_PROPERTY_PREFIX + ".enabled=true")
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
                        CONSTRAINT_CONFIG_PROPERTY_PREFIX + ".enabled=true",
                        CONSTRAINT_CONFIG_PROPERTY_PREFIX + ".expose-technical-details=true",
                        CONSTRAINT_CONFIG_PROPERTY_PREFIX + ".status-code=400"
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