package xyz.catuns.spring.base.dto;

import xyz.catuns.spring.base.model.user.Account;
import xyz.catuns.spring.base.model.user.Session;
import xyz.catuns.spring.base.model.user.UserRoleAuthority;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

public record UserResponse(
        Long id,
        String username,
        String email,
        LocalDateTime createdAt,
        Set<UserRoleAuthority> roles,
        Set<Account> accounts,
        Set<Session> sessions
) implements Serializable {
}
