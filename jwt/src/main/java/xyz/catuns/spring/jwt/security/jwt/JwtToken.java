package xyz.catuns.spring.jwt.security.jwt;

import java.time.Instant;
import java.util.Date;

public record JwtToken(
        String value,
        Instant expiration
) {
}
