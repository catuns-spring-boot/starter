package xyz.catuns.spring.model.user;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Session")
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
