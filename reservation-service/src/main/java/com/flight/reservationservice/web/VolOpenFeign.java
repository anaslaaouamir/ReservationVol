package com.flight.reservationservice.web;

import com.flight.reservationservice.models.Vol;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@FeignClient(name = "OFFREVOL-SERVICE")

public interface VolOpenFeign {
    @GetMapping("/vols")
    public List<Vol> findAll();

    @GetMapping("/vols/{id}")
    public Vol findById(@PathVariable Long id);

    @PutMapping("/{id}/decrement")
    public Vol decrement(@PathVariable Long id);
}
