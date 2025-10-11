package xyz.catuns.spring.jwt.core.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import org.springframework.security.config.Customizer;
import xyz.catuns.spring.jwt.core.model.JwtToken;

public interface JwtService<T> {

    /**
     * Generates a JWT token
     * @param auth auth
     * @return jwt token
     */
    JwtToken generate(T auth);

    /**
     *
     * @param token token value
     * @return {@link T}
     */
    T validate(String token);


    default Customizer<JwtBuilder> customizer() {
        return Customizer.withDefaults();
    }

    Claims getClaims(String token);

}
