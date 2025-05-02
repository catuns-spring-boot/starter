package xyz.catuns.spring.base.model.user;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user_role_authority", schema = "users")
public class UserRoleAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "name", nullable = false)
    protected String name;

    @ManyToMany(mappedBy = "roles")
    protected Set<UserEntity> users = new HashSet<>();

    public GrantedAuthority asAuthority() {
        final String roleName = ("ROLE_" + name).toUpperCase();
        return new SimpleGrantedAuthority(roleName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRoleAuthority that = (UserRoleAuthority) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
