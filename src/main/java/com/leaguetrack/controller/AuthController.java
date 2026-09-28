package com.leaguetrack.controller;

import com.leaguetrack.dto.AuthResponse;
import com.leaguetrack.dto.LoginRequest;
import com.leaguetrack.dto.RegisterRequest;
import com.leaguetrack.entity.User;
import com.leaguetrack.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Register a new user
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        User savedUser = authService.register(user);

        return new AuthResponse(
                "Registration successful",
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );
    }

    // Login an existing user
    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {

        boolean authenticated = authService.login(
                request.getEmail(),
                request.getPassword()
        );

        if (!authenticated) {
            throw new RuntimeException("Invalid email or password");
        }

        User user = authService.getUserByEmail(request.getEmail());

        return new AuthResponse(
                "Login successful",
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}