package com.flight.reservationservice.repositories;

import com.flight.reservationservice.entities.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
}
