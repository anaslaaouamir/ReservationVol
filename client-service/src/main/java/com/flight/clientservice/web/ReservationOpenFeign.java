package com.flight.clientservice.web;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "RESERVATION-SERVICE")
public interface ReservationOpenFeign {

    @DeleteMapping("client_reservations/{id}")
    public void supprimerReservationClient(@PathVariable Long id);

}
