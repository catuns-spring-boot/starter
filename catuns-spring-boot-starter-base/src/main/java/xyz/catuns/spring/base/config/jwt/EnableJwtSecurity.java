package xyz.catuns.spring.base.config.jwt;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(value = JwtSecurityAutoConfiguration.class)
public @interface EnableJwtSecurity {
}
