package xyz.catuns.spring.jwt.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

import static xyz.catuns.spring.jwt.security.jwt.Constants.Jwt.*;

public class JwtService {

    private final String issuer;
    private final long tokenExpiration;
    private final String secret;

    public JwtService(JwtProperties jwtProperties) {
        secret = jwtProperties.secret();
        tokenExpiration = jwtProperties.expiration();
        issuer = jwtProperties.issuer();
    }

    public JwtToken generate(Authentication auth) {
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        Date expiration = this.getExpiration();
        String token = Jwts.builder()
                .issuer(issuer)
                .subject(auth.getName())
                .claim(USERNAME_KEY, auth.getName())
                .claim(AUTHORITY_KEY, extractAuthorities(auth))
                .issuedAt(new Date())
                .expiration(expiration)
                .signWith(secretKey)
                .compact();

        return new JwtToken(token, expiration);
    }

    private Date getExpiration() {
        long now = System.currentTimeMillis();
        return new Date(now + this.tokenExpiration);
    }

    public Authentication validate(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        Claims claims = Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token).getPayload();
        String username = String.valueOf(claims.get(USERNAME_KEY));
        String authorities = String.valueOf(claims.get(AUTHORITY_KEY));
        return new UsernamePasswordAuthenticationToken(username, null,
                AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
    }

    private static String extractAuthorities(Authentication auth) {
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }
}
