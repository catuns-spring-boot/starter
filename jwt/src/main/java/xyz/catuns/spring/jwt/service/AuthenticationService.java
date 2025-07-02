package xyz.catuns.spring.jwt.service;

import jakarta.validation.Valid;
import xyz.catuns.spring.jwt.controller.request.LoginRequest;
import xyz.catuns.spring.jwt.controller.request.RegisterRequest;
import xyz.catuns.spring.jwt.controller.response.LoginResponse;
import xyz.catuns.spring.jwt.controller.response.RegisterResponse;

public interface AuthenticationService<
        Ro extends RegisterResponse,
        Lo extends LoginResponse,
        Re extends RegisterRequest,
        Le extends LoginRequest
> {
    Ro register(@Valid Re registration);
    Lo login(@Valid Le loginRequest);
}
