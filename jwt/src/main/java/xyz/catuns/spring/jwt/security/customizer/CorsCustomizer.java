package xyz.catuns.spring.jwt.security.customizer;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;

public interface CorsCustomizer extends Customizer<CorsConfigurer<HttpSecurity>> {
}
