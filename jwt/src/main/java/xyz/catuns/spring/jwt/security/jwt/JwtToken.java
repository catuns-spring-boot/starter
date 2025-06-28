package xyz.catuns.spring.jwt.security.jwt;

import java.util.Date;

public record JwtToken(
        String accessToken,
        Date expiration
) {
}
