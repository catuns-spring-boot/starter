package xyz.catuns.spring.jwt.model;

import jakarta.persistence.*;
import lombok.Data;

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
    private LocalDateTime expires;

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
        return LocalDateTime.now().isAfter(this.expires);
    }
}
