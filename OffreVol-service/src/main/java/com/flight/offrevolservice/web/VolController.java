package com.flight.offrevolservice.web;

import com.flight.offrevolservice.entities.Vol;
import com.flight.offrevolservice.repositories.VolRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class VolController {

    VolRepository volRepository;
    ReservationOpenFeign reservationOpenFeign;

    VolController(VolRepository volRepository, ReservationOpenFeign reservationOpenFeign) {
        this.volRepository = volRepository;
        this.reservationOpenFeign = reservationOpenFeign;
    }

    // Accessible par le rôle ADMIN uniquement
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/vols")
    public List<Vol> vols() {
        return volRepository.findAll();
    }

    // Accessible par le rôle ADMIN uniquement
    @PreAuthorize("hasAnyRole('ADMIN','CLIENT')")
    @GetMapping("/vols/{id}")
    public Vol unVol(@PathVariable Long id) {
        return volRepository.findById(id).orElseThrow();
    }

    // Accessible par le rôle ADMIN uniquement
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/vols")
    public void createVol(@RequestBody Vol vol) {
        volRepository.save(vol);
    }

    // Accessible par le rôle ADMIN uniquement
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/vols/{id}")
    public void deleteVol(@PathVariable Long id) {
        Vol vol = volRepository.findById(id).orElseThrow();
        reservationOpenFeign.supprimerReservationVol(id);
        volRepository.delete(vol);
    }

    // Accessible par le rôle ADMIN uniquement
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/vols-update/{id}")
    public void updateVol(@PathVariable Long id, @RequestBody Vol vol) {
        Vol vol1 = volRepository.findById(id).orElseThrow();
        BeanUtils.copyProperties(vol, vol1);
        volRepository.save(vol1);
    }

    // Accessible par le rôle CLIENT uniquement
    @PreAuthorize("hasRole('CLIENT')")
    @PutMapping("/{id}/decrement")
    public void decrement(@PathVariable Long id) {
        Vol vol = volRepository.findById(id).orElseThrow();
        vol.setPlacesDisponibles(vol.getPlacesDisponibles() - 1);
        volRepository.save(vol);
    }

    // Accessible par le rôle CLIENT uniquement
    @PreAuthorize("hasAnyRole('ADMIN','CLIENT')")
    @PutMapping("/{id}/increment")
    public void increment(@PathVariable Long id) {
        Vol vol = volRepository.findById(id).orElseThrow();
        vol.setPlacesDisponibles(vol.getPlacesDisponibles() + 1);
        volRepository.save(vol);
    }

    // Accessible par le rôle CLIENT uniquement
    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/ClientChercherVol")
    public List<Vol> chercherVol(
            @RequestParam String villeDepart,
            @RequestParam String villeDestination,
            @RequestParam int day,
            @RequestParam int month,
            @RequestParam int year
    ) {
        LocalDateTime startOfDay = LocalDateTime.of(year, month, day, 0, 0);
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);

        return volRepository.findByVilleDepartAndVilleDestinationAndHeureDepartBetween(
                villeDepart, villeDestination, startOfDay, endOfDay
        );
    }

    // Accessible par le rôle ADMIN uniquement
    @PreAuthorize("hasRole('ADMIN')")
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

        if (day != null && month != null && year != null) {
            startOfDay = LocalDateTime.of(year, month, day, 0, 0);
            endOfDay = startOfDay.plusDays(1).minusSeconds(1);
        }

        return volRepository.searchVols(villeDepart, villeDestination, startOfDay, endOfDay);
    }
}



























































/*
package com.flight.offrevolservice.web;













import com.flight.offrevolservice.entities.Vol;
import com.flight.offrevolservice.repositories.VolRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

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
*/
