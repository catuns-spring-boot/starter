# Catuns.xyz Spring Boot Starters

## Registering `@Entity` classes

`@EntityScan(basePackages = "xyz.catuns.spring.model")` must be added to 

```java
@SpringBootApplication
@EntityScan(basePackages = "xyz.catuns.spring.model")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```