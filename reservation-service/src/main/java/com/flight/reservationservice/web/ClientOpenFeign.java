package com.flight.reservationservice.web;

import com.flight.reservationservice.models.Client;
import com.flight.reservationservice.models.Vol;
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
    public Client findById(@PathVariable Long id);


}
