package xyz.catuns.spring.jwt.config.user;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import xyz.catuns.spring.jwt.controller.request.RegistrationRequest;

import java.util.ArrayList;
import java.util.List;

@Data
@ConfigurationProperties("auth.user")
public class UserConfigurationProperties{
        private Boolean enabled = true;
        private List<RegistrationRequest> users = new ArrayList<>();
        private List<RegistrationRequest> admins = new ArrayList<>();
}