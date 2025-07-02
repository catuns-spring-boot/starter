package xyz.catuns.spring.jwt.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import xyz.catuns.spring.jwt.controller.request.LoginRequest;
import xyz.catuns.spring.jwt.controller.request.RegisterRequest;
import xyz.catuns.spring.jwt.controller.response.LoginResponse;
import xyz.catuns.spring.jwt.controller.response.RegisterResponse;
import xyz.catuns.spring.jwt.mapper.AuthenticationMapper;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractAuthenticationService<
        Ro extends RegisterResponse,
        Lo extends LoginResponse,
        Re extends RegisterRequest,
        Le extends LoginRequest
> implements AuthenticationService<Ro,Lo,Re,Le> {

    protected final AuthenticationManager manager;

    protected AbstractAuthenticationService(AuthenticationManager authenticationManager) {
        this.manager = authenticationManager;
    }

    protected Authentication authenticate(LoginRequest userLogin) {
        Authentication auth = manager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(
                        userLogin.username(),
                        userLogin.password()
                )
        );

        if (auth == null || !auth.isAuthenticated()) {
            throw new BadCredentialsException("Username or password is incorrect");
        }

        return auth;
    }

    protected Set<String> extractAuthorities(Authentication auth) {
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
    }

    abstract protected AuthenticationMapper<?,Ro,Lo,Re> mapper();
}
