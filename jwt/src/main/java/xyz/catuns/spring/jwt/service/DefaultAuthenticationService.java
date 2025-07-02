package xyz.catuns.spring.jwt.service;

import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import xyz.catuns.spring.jwt.dto.UserLoginRequest;
import xyz.catuns.spring.jwt.dto.UserLoginResponse;
import xyz.catuns.spring.jwt.dto.UserRegister;
import xyz.catuns.spring.jwt.dto.UserResponse;
import xyz.catuns.spring.jwt.mapper.AuthenticationMapper;
import xyz.catuns.spring.jwt.mapper.UserEntityMapper;
import xyz.catuns.spring.jwt.model.UserEntity;
import xyz.catuns.spring.jwt.repository.UserEntityRepository;
import xyz.catuns.spring.jwt.security.jwt.JwtService;
import xyz.catuns.spring.jwt.security.jwt.JwtToken;

import java.util.Set;

public class DefaultAuthenticationService extends AbstractAuthenticationService<UserResponse,UserLoginResponse, UserRegister, UserLoginRequest> {

    protected final PasswordEncoder passwordEncoder;
    protected final UserEntityRepository userEntityRepository;
    protected final JwtService jwtService;

    protected DefaultAuthenticationService(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserEntityRepository userEntityRepository, JwtService jwtService) {
        super(authenticationManager);
        this.passwordEncoder = passwordEncoder;
        this.userEntityRepository = userEntityRepository;
        this.jwtService = jwtService;
    }

    @Override
    public UserResponse register(@Valid UserRegister registration) {
        UserEntity userEntity = mapper().map(registration);
        userEntity.setPassword(passwordEncoder.encode(registration.password()));
        userEntity = userEntityRepository.save(userEntity);
        return mapper().toRegisterResponse(userEntity);
    }

    @Override
    public UserLoginResponse login(@Valid UserLoginRequest loginRequest) {
        Authentication auth = authenticate(loginRequest);
        String email = ((UserDetails) auth.getPrincipal()).getUsername();
        Set<String> roles = this.extractAuthorities(auth);
        JwtToken token = jwtService.generate(auth);
        return new UserLoginResponse(token.value(), token.expiration(), email, roles);
    }

    @Override
    protected AuthenticationMapper<UserEntity, UserResponse, UserLoginResponse, UserRegister> mapper() {
        return UserEntityMapper.INSTANCE;
    }
}
