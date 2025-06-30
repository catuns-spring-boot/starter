package xyz.catuns.spring.jwt.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Objects;

@Data
@Entity
@Table(name = "user_roles")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "name", nullable = false)
    protected String name;

    public GrantedAuthority toAuthority() {
        final String roleName = ("ROLE_" + name).toUpperCase();
        return new SimpleGrantedAuthority(roleName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole that = (UserRole) o;
        return Objects.equals(name, that.name);
    }

    public UserRole(String name) {
        setName(name);
    }

    public UserRole() {}


    public void setName(String name) {
        this.name = name.trim().toUpperCase();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }


    @Override
    public String toString() {
        return this.name.toUpperCase();
    }
}
