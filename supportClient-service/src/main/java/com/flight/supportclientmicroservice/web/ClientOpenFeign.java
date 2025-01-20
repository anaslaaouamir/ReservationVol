package com.flight.supportclientmicroservice.web;

import com.flight.supportclientmicroservice.models.Client;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CLIENT-SERVICE")
public interface ClientOpenFeign {

    @GetMapping("/clients/{id}")
    @CircuitBreaker(name="supportServiceCB", fallbackMethod = "getDefaultClient")
    public Client findById(@PathVariable Long id);

    public default Client getDefaultClient(Long id, Throwable t) {
        return Client.builder().idClient(id).nom("default").email("default").telephone("default").build();
    }
}

