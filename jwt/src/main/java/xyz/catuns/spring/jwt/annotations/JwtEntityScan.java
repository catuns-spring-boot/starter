package xyz.catuns.spring.jwt.annotations;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EntityScan(basePackages = "xyz.catuns.spring.jwt.model")
public @interface JwtEntityScan {
}
