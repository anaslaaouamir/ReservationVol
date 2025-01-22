package com.flight.offrevolservice.web;

import com.flight.offrevolservice.entities.Vol;
import com.flight.offrevolservice.repositories.VolRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController


public class VolController {

    VolRepository volRepository;
    ReservationOpenFeign reservationOpenFeign;

    VolController(VolRepository volRepository,ReservationOpenFeign reservationOpenFeign) {
        this.volRepository = volRepository;
        this.reservationOpenFeign = reservationOpenFeign;
    }

    //reservation dispo

    @GetMapping("/vols")
    public List<Vol> vols() {
        return  volRepository.findAll();
    }

    @GetMapping("/vols/{id}")
    public Vol unVol(@PathVariable Long id) {
        return (Vol) volRepository.findById(id).get();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/vols-add")
    public void createVol(@RequestBody Vol vol) {
        volRepository.save(vol);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/vols-delete/{id}")
    public void deleteVol(@PathVariable Long id) {
        Vol vol= volRepository.findById(id).get();
        reservationOpenFeign.supprimerReservationVol(id);
        volRepository.delete(vol);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/vols-update/{id}")
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

    @GetMapping("/ClientChercherVol")
    public List<Vol> chercherVol(
            @RequestParam String villeDepart,
            @RequestParam String villeDestination,
            @RequestParam int day,
            @RequestParam int month,
            @RequestParam int year
    ) {
        // Create LocalDateTime range for the specified date
        LocalDateTime startOfDay = LocalDateTime.of(year, month, day, 0, 0);
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);

        // Fetch flights based on criteria
        return volRepository.findByVilleDepartAndVilleDestinationAndHeureDepartBetween(
                villeDepart, villeDestination, startOfDay, endOfDay
        );
    }

    @GetMapping("/AdminChercherVol")
    public List<Vol> chercherVolFlexible(
            @RequestParam(required = false) String villeDepart,
            @RequestParam(required = false) String villeDestination,
            @RequestParam(required = false) Integer day,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year
    ) {
        LocalDateTime startOfDay = null;
        LocalDateTime endOfDay = null;

        // If date parameters are provided, calculate the start and end of the day
        if (day != null && month != null && year != null) {
            startOfDay = LocalDateTime.of(year, month, day, 0, 0);
            endOfDay = startOfDay.plusDays(1).minusSeconds(1);
        }

        // Call repository method with the provided parameters
        return volRepository.searchVols(villeDepart, villeDestination, startOfDay, endOfDay);
    }

}
