package xyz.catuns.spring.jwt.core.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import xyz.catuns.spring.jwt.autoconfigure.properties.JwtProperties;
import xyz.catuns.spring.jwt.exception.MissingSecretException;
import xyz.catuns.spring.jwt.core.model.JwtToken;

import java.time.Instant;
import java.util.Date;
import java.util.Set;

public class AuthJwtService extends AbstractJwtService<Authentication> {

    public static final String AUTHORITIES_CLAIM_KEY = "authorities";
    public static final String USERNAME_CLAIM_KEY = "username";

    public AuthJwtService(JwtProperties properties) throws MissingSecretException {
        super(properties);
    }

    /**
     * Generates a JWT token using {@link JwtProperties#getExpiration()} duration
     * @param auth auth
     * @return jwt token
     */
    @Override
    public JwtToken generate(Authentication auth) {
        Instant now = Instant.now();
        Instant expiration = now.plus(properties.getExpiration());
        Set<String> authoritiesList = AuthorityUtils.authorityListToSet(auth.getAuthorities());
        JwtBuilder jwtBuilder = Jwts.builder()
                .issuer(properties.getIssuer())
                .subject(auth.getName())
                .claim(USERNAME_CLAIM_KEY, auth.getName())
                .claim(AUTHORITIES_CLAIM_KEY, String.join(",", authoritiesList));

        customizer().customize(jwtBuilder);
        String token = jwtBuilder.issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(getSecretKey())
                .compact();

        return new JwtToken(token, expiration, now, auth.getName());
    }

    /**
     *
     * @param token bearer token
     * @return {@link Authentication}
     */
    @Override
    public Authentication validate(String token) {
        Claims claims = getClaims(token);
        String username = String.valueOf(claims.get(USERNAME_CLAIM_KEY));
        String authorities = String.valueOf(claims.get(AUTHORITIES_CLAIM_KEY));
        return new UsernamePasswordAuthenticationToken(username, null,
                AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
    }

}
