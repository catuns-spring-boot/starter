package xyz.catuns.spring.jwt.annotations.configuration;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableJpaRepositories(basePackages = "xyz.catuns.spring.jwt.domain.repository")
public @interface JwtRepositories {
}
