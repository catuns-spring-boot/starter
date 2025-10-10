package xyz.catuns.spring.jwt.annotations;

import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ComponentScan(basePackages = "xyz.catuns.spring.jwt.mapper")
public @interface JwtMappers {
}
