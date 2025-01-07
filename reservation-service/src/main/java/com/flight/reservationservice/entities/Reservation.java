package com.flight.reservationservice.entities;

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
    private Long idClient;
    private int numeroPlace;
    private LocalDateTime dateReservation;
    private String statut; //annulé ou confirmé

    @Transient
    Client client;

    @Transient
    Vol vol;
}
