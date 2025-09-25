package xyz.catuns.spring.jwt.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
@Table(name = "verification_token", uniqueConstraints = {
        @UniqueConstraint(name = "uc_identifier_token", columnNames = {"identifier", "token"})
})
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "identifier", nullable = false)
    private String identifier;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "expires", nullable = false)
    private Instant expires;

    public VerificationToken(String identifier, String token, Instant expires) {
        this.identifier = identifier;
        this.token = token;
        this.expires = expires;
    }

    public VerificationToken() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VerificationToken that = (VerificationToken) o;
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
