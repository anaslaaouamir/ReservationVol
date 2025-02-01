package com.flight.clientservice.web;

import com.flight.clientservice.entities.Client;
import com.flight.clientservice.exception.ResourceNotFoundException;
import com.flight.clientservice.repositories.ClientRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController


public class ClientController {

    ClientRepository clientRepository;
    ReservationOpenFeign reservationOpenFeign;
    SupportTicketOpenFeign supportTicketOpenFeign;

    ClientController(ClientRepository clientRepository,ReservationOpenFeign reservationOpenFeign,SupportTicketOpenFeign supportTicketOpenFeign) {
        this.clientRepository = clientRepository;
        this.reservationOpenFeign = reservationOpenFeign;
        this.supportTicketOpenFeign=supportTicketOpenFeign;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/clients")
    public List<Client> clients() {
        return  clientRepository.findAll();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    @GetMapping("/clients/{id}")
    public Client unClient(@PathVariable Long id) {
        return (Client) clientRepository.findById(id).get();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public void createClient(@RequestBody Client client) {
        clientRepository.save(client);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/clients/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        try {
            // Vérifier si le client existe
            Client client = clientRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Client non trouvé avec l'id: " + id));

            // Supprimer les réservations
            try {
                reservationOpenFeign.supprimerReservationClient(id);
            } catch (Exception e) {
                System.out.println("Erreur lors de la suppression des réservations: " + e.getMessage());
                // Continuer malgré l'erreur
            }

            // Supprimer les tickets
            try {
                supportTicketOpenFeign.supprimerTicket(id);
            } catch (Exception e) {
                System.out.println("Erreur lors de la suppression des tickets: " + e.getMessage());
                // Continuer malgré l'erreur
            }

            // Supprimer le client
            clientRepository.delete(client);
            return ResponseEntity.ok().build();

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la suppression: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    @PutMapping("/clients/{id}")
    public void updateClient(@PathVariable Long id, @RequestBody Client client) {
        Client existingClient = clientRepository.findById(id).get();
        BeanUtils.copyProperties(client, existingClient, "idClient", "roles","motPasse");
        clientRepository.save(existingClient);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("chercherClients")
    public List<Client> chercherClients(@RequestParam String recherche) {
        return clientRepository.searchClients(recherche);
    }


}
