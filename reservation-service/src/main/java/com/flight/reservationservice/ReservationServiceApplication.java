package com.flight.reservationservice;

import com.flight.reservationservice.entities.Paiement;
import com.flight.reservationservice.entities.Reservation;
import com.flight.reservationservice.models.Client;
import com.flight.reservationservice.repositories.PaiementRepository;
import com.flight.reservationservice.repositories.ReservationRepository;
import com.flight.reservationservice.web.ClientOpenFeign;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.time.LocalDateTime;

@SpringBootApplication

@EnableFeignClients

public class ReservationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservationServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(ReservationRepository reservationRepository, PaiementRepository paiementRepository) {
        return args -> {
            Reservation res1 = Reservation.builder()
                    .numeroPlace(44)
                    .dateReservation(LocalDateTime.of(2025, 2, 5, 9, 30))
                    .statut("accepté")
                    .idClient(1L)
                    .idVol(1L)
                    .build();
            reservationRepository.save(res1);

            Paiement p1=Paiement.builder()
                    .reservation(res1)
                    .montant(3500L)
                    .methodePaiement("Paypal")
                    .datePaiement(LocalDateTime.of(2025, 2, 5, 9, 25))
                    .statut("refuse")
                    .build();

            paiementRepository.save(p1);

        };
    }
}
