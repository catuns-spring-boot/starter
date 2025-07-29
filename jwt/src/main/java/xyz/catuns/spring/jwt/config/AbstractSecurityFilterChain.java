package xyz.catuns.spring.jwt.config;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public class AbstractSecurityFilterChain implements JwtSecurityFilterChain {

    @Override
    public Customizer<HttpSecurity> httpSecurityCustomizer() {
        return (http) -> {};
    }
}
