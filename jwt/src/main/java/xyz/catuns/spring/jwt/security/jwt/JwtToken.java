package xyz.catuns.spring.jwt.security.jwt;

import java.time.Instant;

public record JwtToken(
        String value,
        Instant expiration,
        Instant issuedAt,
        String subject
) {
}
