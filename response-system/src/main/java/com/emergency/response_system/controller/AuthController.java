package com.emergency.response_system.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emergency.response_system.model.User;
import com.emergency.response_system.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody User user) {
        user.setRole("USER");

        Optional<User> existing = userRepo.findByEmail(user.getEmail());
        if (existing.isPresent()) {
            return ResponseEntity.badRequest().body(new AuthResponse("error", null, null, "Email already registered"));
        }

        User saved = userRepo.save(user);
        return ResponseEntity.ok(new AuthResponse("success", saved.getRole(), saved.getId(), "Registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody User user) {
        Optional<User> existing = userRepo.findByEmail(user.getEmail());

        if (existing.isPresent() &&
            existing.get().getPassword().equals(user.getPassword())) {
            User found = existing.get();
            return ResponseEntity.ok(new AuthResponse("success", found.getRole(), found.getId(), "Login success"));
        }

        return ResponseEntity.badRequest().body(new AuthResponse("error", null, null, "Invalid credentials"));
    }

    public static record AuthResponse(String status, String role, Long userId, String message) {}
}