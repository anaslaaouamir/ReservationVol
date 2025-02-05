package com.flight.reservationservice.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter

public class Client {
    private Long idClient;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
}
