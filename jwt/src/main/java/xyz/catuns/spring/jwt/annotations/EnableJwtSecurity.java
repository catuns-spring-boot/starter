package xyz.catuns.spring.jwt.annotations;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@JwtEntityScan
@JwtRepositories
@JwtMappers
public @interface EnableJwtSecurity {
}
