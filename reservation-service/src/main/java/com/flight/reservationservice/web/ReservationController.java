package com.flight.reservationservice.web;

import com.flight.reservationservice.entities.Paiement;
import com.flight.reservationservice.entities.Reservation;
import com.flight.reservationservice.models.Client;
import com.flight.reservationservice.models.Vol;
import com.flight.reservationservice.repositories.PaiementRepository;
import com.flight.reservationservice.repositories.ReservationRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @PostMapping("reservations")
    public void ajouterReservation(@RequestBody Reservation reservation) {
        Vol vol= volOpenFeign.findById(reservation.getIdVol());
         if(vol.getPlacesDisponibles()>0){
             reservation.setStatut("attend paiement");
             reservationRepository.save(reservation);
             //volOpenFeign.decrement(reservation.getIdVol());
         }else {

         }
    }

    @DeleteMapping("reservations/{id}")
    public void supprimerReservation(@PathVariable Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        try {
            if (Objects.equals(reservation.getStatut(), "Payé")) {
                volOpenFeign.increment(reservation.getIdVol());
            }
            // Only delete the reservation if the increment succeeds
            reservationRepository.delete(reservation);
        } catch (Exception e) {
            System.out.println("Failed to increment Vol: " + e.getMessage());
            throw new RuntimeException("Deletion aborted because increment operation failed");
        }
    }


    @GetMapping("/client_reservations/{id}")
    public List<Reservation> clientReservations(@PathVariable Long id) {
        List<Reservation> reservations = reservationRepository.findAll();
        List<Reservation> ress = new ArrayList<>();

        for (Reservation res : reservations) {
            if(res.getIdClient().equals(id)){
                Client cl = clientOpenFeign.findById(res.getIdClient());
                res.setClient(cl);
                Vol vol = volOpenFeign.findById(res.getIdVol());
                res.setVol(vol);
                ress.add(res);
            }
        }
        return ress;
    }

    @GetMapping("/vol_reservations/{id}")
    public List<Reservation> volReservations(@PathVariable Long id) {
        List<Reservation> reservations = reservationRepository.findAll();
        List<Reservation> ress = new ArrayList<>();

        for (Reservation res : reservations) {
            if(res.getIdVol().equals(id)){
                Client cl = clientOpenFeign.findById(res.getIdClient());
                res.setClient(cl);
                Vol vol = volOpenFeign.findById(res.getIdVol());
                res.setVol(vol);
                ress.add(res);
            }
        }
        return ress;
    }

    @PutMapping("/reservations/{id}")
    public void updateClient(@PathVariable Long id, @RequestBody Reservation reservation) {
        Reservation reservation1=reservationRepository.findById(id).get();
        BeanUtils.copyProperties(reservation,reservation1);
        reservationRepository.save(reservation1);
    }

    @DeleteMapping("vol_reservations/{id}")
    public void supprimerReservationVol(@PathVariable Long id){

        List<Reservation> reservations = reservationRepository.findAll();
        for (Reservation res : reservations) {
            if(res.getIdVol().equals(id)){
                supprimerReservation(res.getIdReservation());
            }
        }
    }

    @DeleteMapping("client_reservations/{id}")
    public void supprimerReservationClient(@PathVariable Long id){

        List<Reservation> reservations = reservationRepository.findAll();
        for (Reservation res : reservations) {
            if(res.getIdClient().equals(id)){
                supprimerReservation(res.getIdReservation());
            }
        }
    }

    @GetMapping("/clientVolReserves/{idClient}")
    public List<Vol> clientVolReserves(@PathVariable Long idClient,@RequestParam(required = false) String villeDepart,
                                       @RequestParam(required = false) String villeDestination,
                                       @RequestParam(required = false) Integer day,
                                       @RequestParam(required = false) Integer month,
                                       @RequestParam(required = false) Integer year) {

        List<Vol> vols=volOpenFeign.chercherVolFlexible(villeDepart,villeDestination,day,month,year);
        List<Reservation> reservations = clientReservations(idClient);
        List<Vol> vols1 = new ArrayList<>();
        for(Reservation r:reservations){
            for(Vol v:vols){
                if(r.getIdVol().equals(v.getIdVol())){
                    vols1.add(v);
                }
            }
        }
        return vols1;

    }



}
