package xyz.catuns.spring.jwt.config.jwt;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import xyz.catuns.spring.jwt.config.user.EnableUserEntity;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableUserEntity
@ImportAutoConfiguration(value = JwtSecurityAutoConfiguration.class)
public @interface EnableJwtSecurity {
}
