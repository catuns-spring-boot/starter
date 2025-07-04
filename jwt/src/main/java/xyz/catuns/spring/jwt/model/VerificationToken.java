package xyz.catuns.spring.jwt.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

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

}
