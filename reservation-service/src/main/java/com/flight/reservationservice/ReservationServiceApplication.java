package com.flight.reservationservice;

import com.flight.reservationservice.entities.Reservation;
import com.flight.reservationservice.models.Client;
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
    CommandLineRunner start(ReservationRepository reservationRepository,ClientOpenFeign clientOpenFeign) {
        return args -> {
            Reservation res1 = Reservation.builder()
                    .numeroPlace(44)
                    .dateReservation(LocalDateTime.of(2025, 2, 5, 9, 30))
                    .statut("accepté")
                    .idClient(1L)
                    .idVol(1L)
                    .build();
            reservationRepository.save(res1);

        };
    }
}
