package com.flight.supportclientmicroservice.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.flight.supportclientmicroservice.models.Client;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

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

    private String sujet; // Subject of the issue
    private String description; // Detailed description
    private String statut; // Status (e.g., Open, In Progress, Resolved)
    private LocalDateTime dateCreation; // Creation date
    private LocalDateTime dateMiseAJour; // Last update date

    private Long idClient;
    @Transient
    private Client client;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<TicketMessage> messages; // List of messages associated with this ticket
}
