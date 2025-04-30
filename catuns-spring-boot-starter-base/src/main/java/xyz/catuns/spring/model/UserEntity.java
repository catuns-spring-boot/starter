package xyz.catuns.spring.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Data

@Entity
@Table(name = "user_entities")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "pwd_hash", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "roles")
    private Set<UserRoleAuthority> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Account> accounts = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Session> sessions = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public void addRoles(UserRoleAuthority... role) {
        if (roles == null) {
            roles = new HashSet<>();
        }
        this.roles.addAll(Arrays.asList(role));
    }
}
