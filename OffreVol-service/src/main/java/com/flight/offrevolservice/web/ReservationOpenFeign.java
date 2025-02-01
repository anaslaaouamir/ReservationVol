package com.flight.offrevolservice.web;

import com.flight.offrevolservice.security.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;



@FeignClient(name = "RESERVATION-SERVICE", configuration = FeignClientConfig.class)
public interface ReservationOpenFeign {
    @DeleteMapping("vol_reservations/{id}")
    void supprimerReservationVol(@PathVariable Long id);
}
