package xyz.catuns.spring.jwt.core.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import xyz.catuns.spring.jwt.autoconfigure.properties.JwtProperties;
import xyz.catuns.spring.jwt.exception.MissingSecretException;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

public abstract class AbstractJwtService<T> implements JwtService<T> {

    protected final JwtProperties properties;

    public AbstractJwtService(JwtProperties properties) throws MissingSecretException {
        if (properties.getSecret() == null || properties.getSecret().isEmpty()) {
            throw new MissingSecretException();
        }
        this.properties = properties;
    }

    @Override
    public Claims getClaims(String token) {
        SecretKey secretKey = getSecretKey();
        return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token).getPayload();
    }

    protected SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(properties.getSecret().getBytes(StandardCharsets.UTF_8));
    }


}
