package xyz.catuns.spring.jwt.service;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import xyz.catuns.spring.jwt.controller.request.UserLogin;
import xyz.catuns.spring.jwt.controller.request.UserRegistration;
import xyz.catuns.spring.jwt.controller.request.UserUpdate;
import xyz.catuns.spring.jwt.dto.LoginResponse;
import xyz.catuns.spring.jwt.dto.UserResponse;

public interface UserEntityService {
    UserResponse registerUser(@Valid UserRegistration registration);

    LoginResponse loginUser(@Valid UserLogin userLogin);

    UserResponse getUserAfterLogin(Authentication auth);

    UserResponse updateUser(Long id, @Valid UserUpdate userUpdate);

    UserResponse getUserById(Long id);

    UserResponse registerAdmin(UserRegistration registration);
}
