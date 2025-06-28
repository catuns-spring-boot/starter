package xyz.catuns.spring.jwt.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

import static xyz.catuns.spring.jwt.security.jwt.Constants.Jwt.*;


public class JwtTokenUtil {

    private final String issuer;
    private final long tokenExpiration;
    private final String secret;

    public JwtTokenUtil(Environment env) {
        secret = env.getProperty(SECRET_KEY, SECRET_DEFAULT_VALUE);
        issuer = env.getProperty(ISSUER_KEY, ISSUER_DEFAULT_VALUE);
        tokenExpiration = Long.parseLong(env.getProperty(EXPIRATION_KEY, EXPIRATION_DEFAULT_VALUE));
    }

    public JwtTokenUtil(JwtProperties jwtProperties) {
        secret = jwtProperties.secret();
        tokenExpiration = jwtProperties.expiration();
        issuer = jwtProperties.issuer();
    }

    public JwtToken generate(Authentication auth) {
        return generate(auth, this.secret);
    }

    public JwtToken generate(Authentication auth, String jwtSecret) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + this.tokenExpiration);
        String token = Jwts.builder()
                .issuer(issuer)
                .subject(auth.getName())
                .claim(USERNAME_KEY, auth.getName())
                .claim(AUTHORITY_KEY, extractAuthorities(auth))
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(secretKey)
                .compact();

        return new JwtToken(token, expiration);
    }

    public Authentication validate(String token) {
        return validate(token, this.secret);
    }

    public Authentication validate(String token, String jwtSecret) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        Claims claims = Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token).getPayload();
        String username = String.valueOf(claims.get(USERNAME_KEY));
        String authorities = String.valueOf(claims.get(AUTHORITY_KEY));
        return new UsernamePasswordAuthenticationToken(username, null,
                AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
    }

    public static String extractAuthorities(Authentication auth) {
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }
}
