package xyz.catuns.spring.jwt.annotations.configuration;

import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ComponentScan(basePackages = "xyz.catuns.spring.jwt.domain.mapper")
public @interface JwtMappers {
}
