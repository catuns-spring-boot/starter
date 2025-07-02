package xyz.catuns.spring.jwt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import xyz.catuns.spring.jwt.controller.request.LoginRequest;
import xyz.catuns.spring.jwt.controller.request.RegisterRequest;
import xyz.catuns.spring.jwt.controller.response.LoginResponse;
import xyz.catuns.spring.jwt.controller.response.RegisterResponse;
import xyz.catuns.spring.jwt.security.jwt.Constants;
import xyz.catuns.spring.jwt.service.AuthenticationService;

public class AuthenticationController<
        Ro extends RegisterResponse,
        Lo extends LoginResponse,
        Re extends RegisterRequest,
        Le extends LoginRequest
> {

    protected final AuthenticationService<Ro, Lo, Re, Le> authenticationService;

    public AuthenticationController(AuthenticationService<Ro, Lo, Re, Le> authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    @Operation(
            summary = "Register User",
            description = "REST API to register a new User")
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status CREATED")
    public ResponseEntity<Ro>registerUser(@Valid @RequestBody Re registration){
        Ro user = authenticationService.register(registration);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(user);
    }

    @PostMapping("/login")
    @Operation(
            summary = "Login User",
            description = "REST API to login with username and password")
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK")
    public ResponseEntity<Lo> loginUser(@Valid @RequestBody Le userLogin){
        Lo loginResponse = authenticationService.login(userLogin);
        return ResponseEntity.status(HttpStatus.OK)
                .header(Constants.Jwt.AUTHORIZATION_HEADER_KEY, loginResponse.token())
                .body(loginResponse);
    }
}
