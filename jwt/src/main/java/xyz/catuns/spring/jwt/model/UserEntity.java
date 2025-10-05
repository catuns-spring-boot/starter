package xyz.catuns.spring.jwt.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
@Table(name = "user_entity")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserEntity implements UserDetails {

    @Id
    @Setter(value = AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "email", nullable = false, unique = true)
    protected String email;

    @Column(name = "pwd_hash", nullable = false)
    protected String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    protected final Set<UserRole> roles = new HashSet<>();

    @OneToMany(cascade = {CascadeType.ALL})
    protected final Set<Account> accounts = new HashSet<>();

    @OneToMany(cascade = {CascadeType.ALL})
    protected final Set<Session> sessions = new HashSet<>();

    @OneToMany(cascade = {CascadeType.ALL})
    private final Set<VerificationToken> tokens = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at")
    protected LocalDateTime createdAt;

    public UserEntity(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserEntity() {}

    public void addRoles(String... roleName) {
        Arrays.stream(roleName)
                .map(UserRole::new)
                .forEach(this::addRole);
    }

    private void addRole(UserRole userRole) {
        this.roles.add(userRole);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(UserRole::toAuthority)
                .toList();
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
