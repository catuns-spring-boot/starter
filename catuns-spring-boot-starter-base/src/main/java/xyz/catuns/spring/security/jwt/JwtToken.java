package xyz.catuns.spring.security.jwt;

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

import static xyz.catuns.spring.security.jwt.JwtConstants.JWT_AUTHORITY_KEY;
import static xyz.catuns.spring.security.jwt.JwtConstants.JWT_USERNAME_KEY;

public class JwtToken {

    private final String jwtIssuer;
    private final long jwtTokenExpiration;


    public JwtToken(String issuer, Long expiration) {
        this.jwtIssuer = issuer;
        this.jwtTokenExpiration = expiration;
    }

    public String generate(Authentication auth, String jwtSecret) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        Date tokenExpiration = expiration();
        return Jwts.builder()
                .issuer(jwtIssuer)
                .subject(auth.getName())
                .claim(JWT_USERNAME_KEY, auth.getName())
                .claim(JWT_AUTHORITY_KEY, extractAuthorities(auth))
                .issuedAt(new Date())
                .expiration(tokenExpiration)
                .signWith(secretKey).compact();
    }

    public Authentication validate(String token, String jwtSecret) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        Claims claims = Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token).getPayload();
        String username = String.valueOf(claims.get(JWT_USERNAME_KEY));
        String authorities = String.valueOf(claims.get(JWT_AUTHORITY_KEY));
        return new UsernamePasswordAuthenticationToken(username, null,
                AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
    }

    private Date expiration() {
        long nowMillis = System.currentTimeMillis();
        return new Date(nowMillis + jwtTokenExpiration);
    }

    private static String extractAuthorities(Authentication auth) {
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }
}
