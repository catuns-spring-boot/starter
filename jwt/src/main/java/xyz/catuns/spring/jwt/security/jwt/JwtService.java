package xyz.catuns.spring.jwt.security.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

public interface JwtService {

    /**
     * Generates a JWT token using {@link JwtProperties#getExpiration()} duration
     * @param auth auth
     * @return jwt token
     */
    JwtToken generate(Authentication auth);

    /**
     *
     * @param token bearer token
     * @return {@link Authentication}
     */
    Authentication validate(String token);

    /**
     * Creates jwt token using {@link JwtProperties#getRefresh()} duration
     * @param identifier username field (subject)
     * @return {@link JwtToken}
     */
    JwtToken generateRefresh(String identifier);

    Claims getClaims(String token);

}
