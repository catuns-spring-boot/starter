package xyz.catuns.spring.jwt.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

import static xyz.catuns.spring.jwt.security.jwt.Constants.Jwt.ISSUER_DEFAULT_VALUE;
import static xyz.catuns.spring.jwt.security.jwt.Constants.Jwt.SECRET_DEFAULT_VALUE;

@ConfigurationProperties("auth.jwt")
public record JwtProperties (

    @DefaultValue("36000000") // 10 hours
    Long expiration,
    @DefaultValue(ISSUER_DEFAULT_VALUE)
    String issuer,
    @DefaultValue(SECRET_DEFAULT_VALUE)
    String secret
) {

}
