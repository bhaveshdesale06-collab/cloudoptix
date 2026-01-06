package com.cloudoptix.cloudoptix_backend.controller;

import com.cloudoptix.cloudoptix_backend.dto.LoginRequest;
import com.cloudoptix.cloudoptix_backend.dto.LoginResponse;
import com.cloudoptix.cloudoptix_backend.dto.RegisterRequest;
import com.cloudoptix.cloudoptix_backend.dto.UserResponse;
import com.cloudoptix.cloudoptix_backend.model.User;
import com.cloudoptix.cloudoptix_backend.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserResponse register(@RequestBody RegisterRequest request) {

        User user = userService.registerUser(
                request.getName(),
                request.getEmail(),
                request.getPassword()
        );

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        String token = userService.login(
                request.getEmail(),
                request.getPassword()
        );

        return new LoginResponse(token);
    }
}
