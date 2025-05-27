package xyz.catuns.spring.base.model.user;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "user_entity", schema = "user_entity")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @Column(name = "username")
    protected String username;

    @Column(name = "email", nullable = false, unique = true)
    protected String email;

    @Column(name = "pwd_hash", nullable = false)
    protected String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            schema = "user_entity",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    protected Set<UserRoleAuthority> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    protected Set<Account> accounts = new HashSet<>();

    @OneToMany(mappedBy = "user")
    protected Set<Session> sessions = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at")
    protected LocalDateTime createdAt;

    public void addRoles(String... roleName) {
        if (roles == null) {
            roles = new HashSet<>();
        }

        Arrays.stream(roleName)
                .map(UserRoleAuthority::of)
//                .peek(this::setRoleUser)
                .forEach(this::addRole);
    }

//    private void setRoleUser(UserRoleAuthority userRoleAuthority) {
//        userRoleAuthority.
//    }

    private void addRole(UserRoleAuthority userRoleAuthority) {
        this.roles.add(userRoleAuthority);
    }

    protected void setId(Long id) {
        this.id = id;
    }

}
