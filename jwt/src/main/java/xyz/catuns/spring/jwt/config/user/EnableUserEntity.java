package xyz.catuns.spring.jwt.config.user;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ImportAutoConfiguration(UserAutoConfiguration.class)
public @interface EnableUserEntity {
    String[] entityPackages() default {};
    String[] repositoryPackages() default {};

    String[] relativeEntityPackages() default { "entity" };
    String[] relativeRepositoryPackages() default { "repository" };
}
