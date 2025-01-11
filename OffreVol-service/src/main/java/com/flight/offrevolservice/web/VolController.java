package com.flight.offrevolservice.web;

import com.flight.offrevolservice.entities.Vol;
import com.flight.offrevolservice.repositories.VolRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController


public class VolController {

    VolRepository volRepository;
    ReservationOpenFeign reservationOpenFeign;

    VolController(VolRepository volRepository,ReservationOpenFeign reservationOpenFeign) {
        this.volRepository = volRepository;
        this.reservationOpenFeign = reservationOpenFeign;
    }

    @GetMapping("/vols")
    public List<Vol> vols() {
        return  volRepository.findAll();
    }

    @GetMapping("/vols/{id}")
    public Vol unVol(@PathVariable Long id) {
        return (Vol) volRepository.findById(id).get();
    }

    @PostMapping("/vols")
    public void createVol(@RequestBody Vol vol) {
        volRepository.save(vol);
    }

    @DeleteMapping("/vols/{id}")
    public void deleteVol(@PathVariable Long id) {
        Vol vol= volRepository.findById(id).get();
        reservationOpenFeign.supprimerReservationVol(id);
        volRepository.delete(vol);
    }

    @PutMapping("/vols/{id}")
    public void updateVol(@PathVariable Long id, @RequestBody Vol vol) {
        Vol vol1=(Vol) volRepository.findById(id).get();
        BeanUtils.copyProperties(vol,vol1);
        volRepository.save(vol1);
    }

    //decrementer le nombre des places d'une vol aprés une réservation

    @PutMapping("/{id}/decrement")
    public void decrement(@PathVariable Long id) {
        Vol vol= volRepository.findById(id).get();
        vol.setPlacesDisponibles(vol.getPlacesDisponibles() - 1);
        volRepository.save(vol);
    }

    @PutMapping("/{id}/increment")
    public void increment(@PathVariable Long id) {
        Vol vol= volRepository.findById(id).get();
        vol.setPlacesDisponibles(vol.getPlacesDisponibles() + 1);
        volRepository.save(vol);
    }
}
