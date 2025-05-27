package xyz.catuns.spring.jwt.user;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "session", schema = "user_entity")
public class Session {

    @Id
    private Long id;

    @Column(name = "sessionToken", unique = true, nullable = false)
    private String sessionToken;

    @Column(name = "expires", nullable = false)
    private LocalDateTime expires;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private UserEntity user;


}
