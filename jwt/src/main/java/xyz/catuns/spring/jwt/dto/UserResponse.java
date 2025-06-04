package xyz.catuns.spring.jwt.dto;

import xyz.catuns.spring.jwt.model.Account;
import xyz.catuns.spring.jwt.model.Session;
import xyz.catuns.spring.jwt.model.UserRoleAuthority;

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
