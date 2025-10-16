package xyz.catuns.spring.jwt.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class EmailNotFoundException extends UsernameNotFoundException {
    public EmailNotFoundException(String email) {
        super(email);
    }
}
