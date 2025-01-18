package com.flight.supportclientmicroservice.entities;
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
public class ReponseTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReponse;

    private String contenuReponse; // Contenu de la réponse apportée par l'agent

    private LocalDateTime dateReponse; // Date de la réponse

}
