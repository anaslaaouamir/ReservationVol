package com.flight.clientservice.service;

import com.flight.clientservice.entities.Client;
import com.flight.clientservice.repositories.ClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public ClientService(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Client registerClient(Client client) {
        client.setMotPasse(passwordEncoder.encode(client.getMotPasse()));

        // Par défaut, on assigne le rôle CLIENT
        if (client.getRoles() == null || client.getRoles().isEmpty()) {
            client.setRoles(Collections.singletonList("ROLE_CLIENT"));
        }

        return clientRepository.save(client);
    }

    public Optional<Client> findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }
}