package com.flight.clientservice.web;

import com.flight.clientservice.entities.Client;
import com.flight.clientservice.repositories.ClientRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class ClientController {

    ClientRepository clientRepository;
    ReservationOpenFeign reservationOpenFeign;

    ClientController(ClientRepository clientRepository,ReservationOpenFeign reservationOpenFeign) {
        this.clientRepository = clientRepository;
        this.reservationOpenFeign = reservationOpenFeign;
    }


    @GetMapping("/clients")
    public List<Client> clients() {
        return  clientRepository.findAll();
    }

    @GetMapping("/clients/{id}")
    public Client unClient(@PathVariable Long id) {
        return (Client) clientRepository.findById(id).get();
    }

    @PostMapping("/clients")
    public void createClient(@RequestBody Client client) {
        clientRepository.save(client);
    }

    @DeleteMapping("/clients/{id}")
    public void deleteClient(@PathVariable Long id) {
        reservationOpenFeign.supprimerReservationClient(id);
        Client cl= clientRepository.findById(id).get();
        clientRepository.delete(cl);
    }

    @PutMapping("/clients/{id}")
    public void updateClient(@PathVariable Long id, @RequestBody Client client) {
        Client client1=(Client) clientRepository.findById(id).get();
        BeanUtils.copyProperties(client,client1);
        clientRepository.save(client1);
    }


}
