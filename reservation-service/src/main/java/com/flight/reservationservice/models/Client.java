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
    private String email;
    private String motPasse;
    private String telephone;
}
