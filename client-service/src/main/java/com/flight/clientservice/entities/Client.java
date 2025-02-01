package com.flight.clientservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString


public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClient;
    private String nom;
    private String email;
    private String motPasse;
    private String telephone;
    @ElementCollection
    private List<String> roles;
}
