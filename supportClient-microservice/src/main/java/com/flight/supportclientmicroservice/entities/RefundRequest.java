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
public class RefundRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRemboursement;

    private String statutRemboursement; // Statut (Pending, Approved, Rejected)

    private Long montantRembourse; // Montant du remboursement

    private LocalDateTime dateTraitement; // Date de traitement du remboursement
}
