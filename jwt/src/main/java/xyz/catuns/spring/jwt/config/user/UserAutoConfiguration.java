package xyz.catuns.spring.jwt.config.user;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(UserConfigurationProperties.class)
public class UserAutoConfiguration {

}
