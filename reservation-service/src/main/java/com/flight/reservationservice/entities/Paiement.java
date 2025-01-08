package com.flight.reservationservice.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
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

public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPaiement;

    private Long montant;

    private String methodePaiement;

    private LocalDateTime datePaiement;

    private String statut; // e.g., Terminé, Échoué

    @OneToOne
    @JoinColumn(name="idReservation")
    @JsonBackReference

    private Reservation reservation;

}
