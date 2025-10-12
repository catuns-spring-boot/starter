package xyz.catuns.spring.jwt.annotations;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import xyz.catuns.spring.jwt.config.JwtDomainRegistrar;

import java.lang.annotation.*;

/**
 * <h1>Enable JWT Security</h1>
 * <p>
 * Usage:
 * <p>
 * // Basic usage (no domain entities)
 *
 * @EnableJwtSecurity With domain entities
 * <pre>
 * @EnableJwtSecurity(
 *     entityPackages = "com.myapp.domain",
 *     userEntityClass = MyUser.class
 * )
 * </pre>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ImportAutoConfiguration(
        classes = {
                JwtDomainRegistrar.class
        },
        exclude = {
                UserDetailsServiceAutoConfiguration.class
        })
public @interface EnableJwtSecurity {

    /**
     * Packages to scan for domain entities and repositories
     * If specified, enables full domain scanning
     */
    String[] scanPackages() default {};

    /**
     * Concrete UserEntity implementation class
     * Must extend xyz.catuns.spring.jwt.model.UserEntity
     */
    Class<?> userEntityClass() default Object.class;

    /**
     * Concrete RoleEntity implementation class
     * Must extend xyz.catuns.spring.jwt.model.RoleEntity
     */
    Class<?> roleEntityClass() default Object.class;

    /**
     * Concrete UserEntityRepository implementation interface
     * Must extend xyz.catuns.spring.jwt.repository.UserEntityRepository
     */
    Class<?> userRepositoryClass() default Object.class;

    /**
     * Enable security filter chain auto-configuration
     */
    boolean enableSecurityChain() default true;

    /**
     * Enable authentication manager auto-configuration
     */
    boolean enableAuthenticationManager() default true;

    /**
     * Enable UserDetailsService using UserEntityService
     * Only applicable when domain entities are configured
     */
    boolean enableUserDetailsService() default true;
}
