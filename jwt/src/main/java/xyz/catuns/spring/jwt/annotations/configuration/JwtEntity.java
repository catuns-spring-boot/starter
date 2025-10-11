package xyz.catuns.spring.jwt.annotations.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EntityScan(basePackages = "xyz.catuns.spring.jwt.domain.entity")
public @interface JwtEntity {
}
