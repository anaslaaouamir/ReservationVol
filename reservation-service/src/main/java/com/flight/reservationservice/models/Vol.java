package com.flight.reservationservice.models;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Vol {

    private long idVol;

    private String villeDepart;

    private String villeDestination;

    private LocalDateTime heureDepart;

    private LocalDateTime heureArrivee;

    private long prix;

    private int placesDisponibles;

    private String statut;

}
