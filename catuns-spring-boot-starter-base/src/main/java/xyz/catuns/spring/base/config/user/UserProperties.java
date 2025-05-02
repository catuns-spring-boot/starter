package xyz.catuns.spring.base.config.user;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;


@ConfigurationProperties("auth.admin")
public record UserProperties(
        @DefaultValue("admin@catuns.xyz")
        String email,
        String password,
        @DefaultValue("true")
        Boolean enabled
) {}