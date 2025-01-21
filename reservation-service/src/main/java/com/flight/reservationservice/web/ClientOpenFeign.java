package com.flight.reservationservice.web;

import com.flight.reservationservice.models.Client;
import com.flight.reservationservice.models.Vol;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "CLIENT-SERVICE")
public interface ClientOpenFeign {
    @GetMapping("/clients")
    public List<Client> findAll();

    @GetMapping("/clients/{id}")
    @CircuitBreaker(name="reservationServiceCB", fallbackMethod = "getDefaultClient")
    public Client findById(@PathVariable Long id);

    public default Client getDefaultClient(Long id, Throwable t) {
        return Client.builder().idClient(id).nom("default").email("default").telephone("default").build();
    }

}
