package com.flight.supportclientmicroservice.entities;

import com.flight.supportclientmicroservice.models.Client;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class SupportTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTicket;

    private String sujet; // Sujet du problème ou demande
    private String description; // Description détaillée du problème
    private String statut; // Statut (Open, In Progress, Resolved, etc.)
    private LocalDateTime dateCreation; // Date de création
    private LocalDateTime dateMiseAJour; // Date de dernière mise à jour

    private Long idClient;
    @Transient
    private Client client;

}

