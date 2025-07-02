package xyz.catuns.spring.jwt.config.jwt;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ImportAutoConfiguration(value = SecurityAutoConfiguration.class)
public @interface EnableJwtSecurity {
}
