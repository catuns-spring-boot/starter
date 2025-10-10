package xyz.catuns.spring.jwt.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

import static xyz.catuns.spring.jwt.security.jwt.Constants.Jwt.AUTHORITIES_CLAIM_KEY;
import static xyz.catuns.spring.jwt.security.jwt.Constants.Jwt.USERNAME_CLAIM_KEY;

public class DefaultJwtService implements JwtService {

    private final JwtProperties properties;

    public DefaultJwtService(JwtProperties properties) {
        Objects.requireNonNull(properties.getSecret(), "Must define JWT secret");
        this.properties = properties;
    }

    @Override
    public JwtToken generate(Authentication auth) {
        SecretKey secretKey = getSecretKey();
        Instant now = Instant.now();
        Instant expiration = now.plus(properties.getExpiration());
        String token = Jwts.builder()
                .issuer(properties.getIssuer())
                .subject(auth.getName())
                .claim(USERNAME_CLAIM_KEY, auth.getName())
                .claim(AUTHORITIES_CLAIM_KEY, String.join(",",
                        AuthorityUtils.authorityListToSet(auth.getAuthorities())))
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(secretKey)
                .compact();
        return new JwtToken(token, expiration, now, auth.getName());
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(properties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public Authentication validate(String token) {
        Claims claims = getClaims(token);
        String username = String.valueOf(claims.get(USERNAME_CLAIM_KEY));
        String authorities = String.valueOf(claims.get(AUTHORITIES_CLAIM_KEY));
        return new UsernamePasswordAuthenticationToken(username, null,
                AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
    }


    @Override
    public JwtToken generateRefresh(String identifier) {
        SecretKey secretKey = getSecretKey();
        Instant now = Instant.now();
        Instant expiration = now.plus(properties.getRefresh());
        String token = Jwts.builder()
                .issuer(properties.getIssuer())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .claim(USERNAME_CLAIM_KEY, identifier)
                .signWith(secretKey)
                .compact();

        return new JwtToken(token, expiration, now, identifier);
    }

    @Override
    public Claims getClaims(String token) {
        SecretKey secretKey = getSecretKey();
        return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token).getPayload();
    }
}
