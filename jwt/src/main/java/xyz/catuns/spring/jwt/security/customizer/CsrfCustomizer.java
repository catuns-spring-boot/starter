package xyz.catuns.spring.jwt.security.customizer;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;

public interface CsrfCustomizer extends Customizer<CsrfConfigurer<HttpSecurity>> {
}
