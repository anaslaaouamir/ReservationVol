package com.flight.offrevolservice.web;

import com.flight.offrevolservice.entities.Vol;
import com.flight.offrevolservice.repositories.VolRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class VolController {

    VolRepository volRepository;

    VolController(VolRepository volRepository) {
        this.volRepository = volRepository;
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
        volRepository.delete(vol);
    }

    @PutMapping("/vols/{id}")
    public void updateVol(@PathVariable Long id, @RequestBody Vol vol) {
        Vol vol1=(Vol) volRepository.findById(id).get();
        BeanUtils.copyProperties(vol,vol1);
        volRepository.save(vol1);
    }
}
