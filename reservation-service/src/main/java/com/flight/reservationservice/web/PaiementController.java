package com.flight.reservationservice.web;

import com.flight.reservationservice.entities.Paiement;
import com.flight.reservationservice.repositories.PaiementRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class PaiementController {

    PaiementRepository paiementRepository;

    public PaiementController(PaiementRepository paiementRepository){
        this.paiementRepository = paiementRepository;
    }


    @GetMapping("/paiements")
    public List<Paiement> paiements() {
        return  paiementRepository.findAll();
    }

    @GetMapping("/paiements/{id}")
    public Paiement unpaiement(@PathVariable Long id) {
        return paiementRepository.findById(id).get();
    }

}
