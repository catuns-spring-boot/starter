package xyz.catuns.spring.jwt.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import xyz.catuns.spring.base.exception.UserNotFoundException;
import xyz.catuns.spring.jwt.controller.request.UserLogin;
import xyz.catuns.spring.jwt.controller.request.UserRegistration;
import xyz.catuns.spring.jwt.controller.request.UserUpdate;
import xyz.catuns.spring.jwt.dto.LoginResponse;
import xyz.catuns.spring.jwt.dto.UserResponse;
import xyz.catuns.spring.jwt.mapper.UserEntityMapper;
import xyz.catuns.spring.jwt.repository.UserEntityRepository;
import xyz.catuns.spring.jwt.security.jwt.JwtProperties;
import xyz.catuns.spring.jwt.security.jwt.JwtToken;
import xyz.catuns.spring.jwt.security.jwt.JwtTokenUtil;
import xyz.catuns.spring.jwt.model.UserEntity;

public class UserEntityServiceImpl implements UserEntityService {

    private static final UserEntityMapper mapper = UserEntityMapper.INSTANCE;

    private final UserEntityRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProperties jwtProperties;

    public UserEntityServiceImpl(
            UserEntityRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtProperties jwtProperties
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtProperties = jwtProperties;
    }

    @Override
    public UserResponse registerUser(UserRegistration registration) {
        UserEntity user = mapper.map(registration);
        user.setPassword(passwordEncoder.encode(registration.password()));
        UserEntity saved = userRepository.save(user);
        return mapper.toResponse(saved);
    }

    @Override
    public LoginResponse loginUser(UserLogin userLogin) {
        Authentication auth = authenticateLogin(userLogin);
        if (auth == null || !auth.isAuthenticated()) {
            throw new BadCredentialsException("Username or password is incorrect");
        }
        String email = ((UserDetails) auth.getPrincipal()).getUsername();
        String roles = JwtTokenUtil.extractAuthorities(auth);
        JwtToken token = new JwtTokenUtil(jwtProperties)
                .generate(auth, jwtProperties.secret());

        return new LoginResponse(token, email, roles);
    }

    private Authentication authenticateLogin(UserLogin userLogin) {
        return authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(
                        userLogin.email(),
                        userLogin.password()
                )
        );
    }

    @Override
    public UserResponse getUserAfterLogin(Authentication auth) {
        UserEntity user = userRepository.findByEmail(auth.getName())
                .orElse(null);
        return mapper.toResponse(user);
    }

    @Override
    public UserResponse updateUser(Long id, UserUpdate userUpdate) {
        UserEntity user = findById(id);
        mapper.update(userUpdate, user);
        UserEntity saved = userRepository.save(user);
        return mapper.toResponse(saved);
    }

    private UserEntity findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public UserResponse getUserById(Long id) {
        UserEntity user = findById(id);
        return mapper.toResponse(user);
    }

    @Override
    public UserResponse registerAdmin(UserRegistration registration) {
        UserEntity user = mapper.map(registration);
        user.setPassword(passwordEncoder.encode(registration.password()));
        user.addRoles("admin");
        UserEntity saved = userRepository.save(user);
        return mapper.toResponse(saved);
    }
}
