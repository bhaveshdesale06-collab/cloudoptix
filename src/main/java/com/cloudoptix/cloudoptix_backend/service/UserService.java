package com.cloudoptix.cloudoptix_backend.service;

import com.cloudoptix.cloudoptix_backend.exception.UnauthorizedException;
import com.cloudoptix.cloudoptix_backend.model.User;
import com.cloudoptix.cloudoptix_backend.repository.UserRepository;
import com.cloudoptix.cloudoptix_backend.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.cloudoptix.cloudoptix_backend.exception.BadRequestException;



@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // =========================
    // REGISTER USER
    // =========================
    public User registerUser(String name, String email, String password) {

        if (userRepository.findByEmail(email).isPresent()) {
            throw new BadRequestException("Email already registered");

        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("ROLE_USER");


        return userRepository.save(user);
    }

    // =========================
    // LOGIN USER + JWT
    // =========================
    public String login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UnauthorizedException("Invalid credentials");

        }

        return jwtUtil.generateToken(user.getEmail(), user.getRole());

    }
}
