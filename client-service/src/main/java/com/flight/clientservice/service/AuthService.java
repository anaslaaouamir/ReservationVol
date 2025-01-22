package com.flight.clientservice.service;

import com.flight.clientservice.entities.Client;
import com.flight.clientservice.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Simule une base de données de sessions (token -> email)
    private final Map<String, String> sessions = new HashMap<>();

    /**
     * Enregistre un nouveau client.
     */
    public Client register(String nom, String email, String motPasse, String telephone) {
        // Vérifie si l'email est déjà utilisé
        if (clientRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        // Encode le mot de passe
        String encodedPassword = passwordEncoder.encode(motPasse);

        // Crée un nouveau client
        Client client = new Client();
        client.setNom(nom);
        client.setEmail(email);
        client.setMotPasse(encodedPassword);
        client.setTelephone(telephone);

        // Sauvegarde le client dans la base de données
        return clientRepository.save(client);
    }


    public String login(String email, String password) {
        Client client = clientRepository.findByEmail(email).orElse(null);
        if (client != null && passwordEncoder.matches(password, client.getMotPasse())) {
            String token = UUID.randomUUID().toString();
            sessions.put(token, email);
            return token;
        }
        return null; // Authentification échouée
    }
    /**
     * Vérifie si un token est valide.
     */
    public boolean isTokenValid(String token) {
        return sessions.containsKey(token);
    }

    /**
     * Déconnecte un client en supprimant son token.
     */
    public void logout(String token) {
        sessions.remove(token);
    }
}