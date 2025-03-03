package com.flight.reservationservice.repositories;

import com.flight.reservationservice.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByIdVol(Long idVol);
}
