package com.flight.clientservice.web;

import com.flight.clientservice.entities.Client;
import com.flight.clientservice.security.JwtUtil;
import com.flight.clientservice.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private ClientService clientService;

    private PasswordEncoder passwordEncoder;

    private JwtUtil jwtUtil;

    AuthController( ClientService clientService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.clientService = clientService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerClient(@RequestBody Client client) {
        Optional<Client> existingClient = clientService.findByEmail(client.getEmail());
        if (existingClient.isPresent()) {
            return ResponseEntity.badRequest().body("Email already in use");
        }
        Client registeredClient = clientService.registerClient(client);
        return ResponseEntity.ok(registeredClient);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginClient(@RequestBody Client loginRequest) {
        Optional<Client> clientOptional = clientService.findByEmail(loginRequest.getEmail());
        if (clientOptional.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid email or password"));
        }
        Client client = clientOptional.get();
        if (!passwordEncoder.matches(loginRequest.getMotPasse(), client.getMotPasse())) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid email or password"));
        }
        String token = jwtUtil.generateToken(client.getEmail(), client.getNom(), client.getRoles());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", client);
        return ResponseEntity.ok(response);
    }
}
