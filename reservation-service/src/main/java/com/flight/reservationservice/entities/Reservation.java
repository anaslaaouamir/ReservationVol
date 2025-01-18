package com.flight.reservationservice.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.flight.reservationservice.models.Client;
import com.flight.reservationservice.models.Vol;
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

public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservation;

    private int numeroPlace;
    private LocalDateTime dateReservation;
    private String statut;


    private Long idClient;
    @Transient
    private Client client;

    private Long idVol;
    @Transient
    private Vol vol;


    @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Paiement paiement;
}
