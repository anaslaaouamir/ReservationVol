package com.flight.offrevolservice.web;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "RESERVATION-SERVICE")
public interface ReservationOpenFeign {

    @DeleteMapping("vol_reservations/{id}")
    public void supprimerReservationVol(@PathVariable Long id);

}
