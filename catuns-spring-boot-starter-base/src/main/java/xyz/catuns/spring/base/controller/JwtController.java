package xyz.catuns.spring.base.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import xyz.catuns.spring.base.controller.request.UserLogin;
import xyz.catuns.spring.base.controller.request.UserRegistration;
import xyz.catuns.spring.base.controller.request.UserUpdate;
import xyz.catuns.spring.base.dto.LoginResponse;
import xyz.catuns.spring.base.dto.UserResponse;
import xyz.catuns.spring.base.service.UserEntityService;

import static xyz.catuns.spring.base.security.jwt.JwtConstants.JWT_HEADER;

public class JwtController {

    private final UserEntityService userService;

    public JwtController(UserEntityService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @Operation(
            summary = "Register User",
            description = "REST API to register a new User")
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status CREATED")
    public ResponseEntity<UserResponse>registerUser(@Valid @RequestBody UserRegistration registration){
        UserResponse user = userService.registerUser(registration);
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
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody UserLogin userLogin){
        LoginResponse loginResponse = userService.loginUser(userLogin);
        return ResponseEntity.status(HttpStatus.OK)
                .header(JWT_HEADER, loginResponse.accessToken())
                .body(loginResponse);
    }

    @GetMapping("/user")
    @Operation(
            summary = "Get User After Login",
            description = "REST API to FETCH authorized User details")
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK")
    public ResponseEntity<UserResponse> getUserAfterLogin(Authentication auth){
        UserResponse user = userService.getUserAfterLogin(auth);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update User",
            description = "REST API to update User details")
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdate userUpdate
    ){
        UserResponse updatedUser = userService.updateUser(id, userUpdate);
        return ResponseEntity.status(HttpStatus.OK)
                .body(updatedUser);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get User By Id",
            description = "REST API to FETCH a User")
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id){
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
