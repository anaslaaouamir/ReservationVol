package com.flight.offrevolservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class Vol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idVol;

    private String villeDepart;

    private String villeDestination;

    private LocalDateTime heureDepart;

    private LocalDateTime heureArrivee;

    private long prix;

    private int placesDisponibles;

    private String statut; // Exemples : "Programmé", "Retardé", "Annulé"
}
