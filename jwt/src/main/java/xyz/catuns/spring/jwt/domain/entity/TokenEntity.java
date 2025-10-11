package xyz.catuns.spring.jwt.domain.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;


public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "identifier", nullable = false)
    private String identifier;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "expires", nullable = false)
    private Instant expires;

    @Column(name = "issued_at", nullable = false)
    private Instant issuedAt;


    public TokenEntity(String identifier, String token, Instant expires, Instant issuedAt) {
        this.identifier = identifier;
        this.token = token;
        this.expires = expires;
        this.issuedAt = issuedAt;
    }

    public TokenEntity() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenEntity that = (TokenEntity) o;
        return Objects.equals(identifier, that.identifier) && Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, token);
    }

    public boolean isExpired() {
        return Instant.now().isAfter(this.expires);
    }
}
