package xyz.catuns.spring.jwt.config.user;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Data
@ConfigurationProperties("auth.user")
public class UserConfigurationProperties{
        private Boolean enabled = true;
        private List<User> users = new ArrayList<>();

        public record User(String email, String password, List<String> roles) {}
}