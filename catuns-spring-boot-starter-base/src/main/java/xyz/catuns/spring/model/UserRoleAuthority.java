package xyz.catuns.spring.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

public class UserRoleAuthority implements GrantedAuthority {

    private final String role;

    public UserRoleAuthority(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return "Role_" + role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRoleAuthority that = (UserRoleAuthority) o;
        return Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(role);
    }
}
