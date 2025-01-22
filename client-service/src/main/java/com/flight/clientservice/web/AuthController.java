package com.flight.clientservice.web;

import com.flight.clientservice.dto.LoginRequest;
import com.flight.clientservice.dto.RegisterRequest;
import com.flight.clientservice.entities.Client;
import com.flight.clientservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<Client> register(@RequestBody RegisterRequest request) {
        Client client = authService.register(
                request.getNom(),
                request.getEmail(),
                request.getMotPasse(),
                request.getTelephone()
        );
        return ResponseEntity.ok(client);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        if (request.getMotPasse() == null) {
            return ResponseEntity.badRequest().body("Password cannot be null");
        }
        String token = authService.login(request.getEmail(), request.getMotPasse());
        if (token != null) {
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return ResponseEntity.ok("Logged out successfully");
    }
    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token) {
        if (authService.isTokenValid(token)) {
            return ResponseEntity.ok("Token is valid");
        }
        return ResponseEntity.status(401).body("Invalid token");
    }
}