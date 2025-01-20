package com.flight.supportclientmicroservice.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder

public class Client {
    private Long idClient;
    private String nom;
    private String email;
    //private String motPasse;
    private String telephone;
}
