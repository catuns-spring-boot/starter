package xyz.catuns.spring.jwt.security.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

import static xyz.catuns.spring.jwt.security.jwt.Constants.Jwt.ISSUER_DEFAULT_VALUE;

@Data
@ConfigurationProperties("auth.jwt")
public class JwtProperties {

    private String issuer = ISSUER_DEFAULT_VALUE;
    private String secret;
    private Duration refresh = Duration.ofDays(14);
    private Duration expiration = Duration.ofHours(10);

}
