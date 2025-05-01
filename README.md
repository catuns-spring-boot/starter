# Catuns.xyz Spring Boot Starters

## Usage

### [`@EnableJwtSecurityAutoConfiguration`](catuns-spring-boot-starter-base/src/main/java/xyz/catuns/spring/base/config/EnableJwtSecurityAutoConfiguration.java)

Configuration for default jwt Security. 

- Recommended to use With a `SecurityConfig` class
- Requires `spring.security`
- Property `auth.jwt.enabled` must be true (default: `true`)
- Beans (Allows overriding): 
  - `SecurityFilterChain`
  - `AuthenticationManager`
  - `PasswordEncoder`

**Authorized requests**
```java

@Bean
@ConditionalOnMissingBean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(requests -> requests
            .requestMatchers(
                    "/api/users/user"
            ).authenticated()
            .requestMatchers(
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/error",
                    "/api/users/login",
                    "/api/users/register")
            .permitAll()
            .anyRequest().authenticated());
}
```

### [`@EnableUserAutoConfiguration`](catuns-spring-boot-starter-base/src/main/java/xyz/catuns/spring/base/config/EnableUserAutoConfiguration.java)

Configuration for User management. Create entities to use with
`UserDetailsService`.

- Recommended to use With a `MainApplication` class
- Requires `spring.data.jpa`
- Beans (Allows overriding): 
  - `UserDetailsService`


## Registering `@Entity` classes

`@EntityScan(basePackages = "xyz.catuns.spring.base.model")` must be added to 

```java
@SpringBootApplication
@EntityScan(basePackages = "xyz.catuns.spring.base.model")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```