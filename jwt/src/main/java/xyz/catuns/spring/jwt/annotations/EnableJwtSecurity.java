package xyz.catuns.spring.jwt.annotations;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import xyz.catuns.spring.jwt.config.SecurityAutoConfiguration;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ImportAutoConfiguration(value = SecurityAutoConfiguration.class)
public @interface EnableJwtSecurity {
}
