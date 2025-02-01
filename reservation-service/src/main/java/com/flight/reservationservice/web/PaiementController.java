package com.flight.reservationservice.web;

import com.flight.reservationservice.entities.Paiement;
import com.flight.reservationservice.entities.Reservation;
import com.flight.reservationservice.models.Vol;
import com.flight.reservationservice.repositories.PaiementRepository;
import com.flight.reservationservice.repositories.ReservationRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class PaiementController {

    PaiementRepository paiementRepository;
    ReservationRepository reservationRepository;
    VolOpenFeign volOpenFeign;

    public PaiementController(PaiementRepository paiementRepository,ReservationRepository reservationRepository,VolOpenFeign volOpenFeign){
        this.paiementRepository = paiementRepository;
        this.reservationRepository = reservationRepository;
        this.volOpenFeign = volOpenFeign;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/paiements")
    public List<Paiement> paiements() {
        return  paiementRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/paiements/{id}")
    public Paiement unpaiement(@PathVariable Long id) {
        return paiementRepository.findById(id).get();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/reservation_paiement/{id}")
    public Paiement reservationPaiement(@PathVariable Long id) {
        Reservation res = reservationRepository.findById(id).get();
        return  res.getPaiement();
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/paiements/{id}")
    public void createPaiement(@RequestBody Paiement paiement, @PathVariable Long id) {
        Reservation reservation=reservationRepository.findById(id).get();
        Vol vol=volOpenFeign.findById(reservation.getIdVol());
            if(vol.getPlacesDisponibles()>0){
                reservation.setStatut("Payé");
                reservationRepository.save(reservation);
                volOpenFeign.decrement(reservation.getIdVol());
                paiement.setReservation(reservation);
                paiementRepository.save(paiement);
            }
    }


}
