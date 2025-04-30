package xyz.catuns.spring.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(UserAutoConfiguration.class)
public @interface EnableUserAutoConfiguration {
}
