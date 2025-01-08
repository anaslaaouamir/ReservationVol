package com.flight.reservationservice.web;

import com.flight.reservationservice.entities.Paiement;
import com.flight.reservationservice.entities.Reservation;
import com.flight.reservationservice.models.Client;
import com.flight.reservationservice.models.Vol;
import com.flight.reservationservice.repositories.PaiementRepository;
import com.flight.reservationservice.repositories.ReservationRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController

public class ReservationController {


    public ReservationController(ReservationRepository reservationRepository, ClientOpenFeign clientOpenFeign, VolOpenFeign volOpenFeign) {
        this.reservationRepository = reservationRepository;
        this.clientOpenFeign = clientOpenFeign;
        this.volOpenFeign = volOpenFeign;
    }

    private final ReservationRepository reservationRepository;
    private final ClientOpenFeign clientOpenFeign;
    private final VolOpenFeign volOpenFeign;

    @GetMapping("/reservations/{id}")
    public Reservation uneRservation(@PathVariable Long id) {
        Reservation res = reservationRepository.findById(id).get();
        Client cl = clientOpenFeign.findById(res.getIdClient());
        res.setClient(cl);

        Vol vol = volOpenFeign.findById(res.getIdVol());
        res.setVol(vol);

        return res;
    }

    @GetMapping("/reservations")
    public List<Reservation> allReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        List<Reservation> ress = new ArrayList<>();

        for (Reservation res : reservations) {
            Client cl = clientOpenFeign.findById(res.getIdClient());
            res.setClient(cl);
            Vol vol = volOpenFeign.findById(res.getIdVol());
            res.setVol(vol);
            ress.add(res);
        }
        return ress;
    }


}
