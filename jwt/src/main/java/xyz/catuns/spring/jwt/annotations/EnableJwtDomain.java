package xyz.catuns.spring.jwt.annotations;

import xyz.catuns.spring.jwt.annotations.configuration.JwtEntity;
import xyz.catuns.spring.jwt.annotations.configuration.JwtMappers;
import xyz.catuns.spring.jwt.annotations.configuration.JwtRepositories;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@JwtEntity
@JwtRepositories
@JwtMappers
public @interface EnableJwtDomain {
}
