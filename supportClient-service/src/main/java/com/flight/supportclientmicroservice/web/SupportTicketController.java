package com.flight.supportclientmicroservice.web;

import com.flight.supportclientmicroservice.entities.SupportTicket;
import com.flight.supportclientmicroservice.entities.TicketMessage;
import com.flight.supportclientmicroservice.models.Client;
import com.flight.supportclientmicroservice.repositories.SupportTicketRepository;
import com.flight.supportclientmicroservice.repositories.TicketMessageRepository;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController

public class SupportTicketController {


    public SupportTicketController(SupportTicketRepository supportTicketRepository, ClientOpenFeign clientOpenFeign, TicketMessageRepository ticketMessageRepository) {
        this.clientOpenFeign = clientOpenFeign;
        this.supportTicketRepository = supportTicketRepository;
        this.ticketMessageRepository = ticketMessageRepository;

    }

    private ClientOpenFeign clientOpenFeign;
    private SupportTicketRepository supportTicketRepository;
    private TicketMessageRepository ticketMessageRepository;


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/stickets")
    public List<SupportTicket> allSupportTickets() {
        List<SupportTicket> supportTickets = supportTicketRepository.findAll();
        List<SupportTicket> my_supportTickets = new ArrayList<>();

        for (SupportTicket res : supportTickets) {
            Client cl = clientOpenFeign.findById(res.getIdClient());
            res.setClient(cl);
            my_supportTickets.add(res);
        }
        return my_supportTickets;
    }

    @PreAuthorize("hasAnyRole('ADMIN','CLIENT')")
    @GetMapping("/stickets/{id}")
    public List<SupportTicket> clientTickets(@PathVariable Long id) {
        List<SupportTicket> tickets = supportTicketRepository.findAll();
        List<SupportTicket> ress = new ArrayList<>();

        for (SupportTicket res : tickets) {
            if(res.getIdClient().equals(id)){
                Client cl = clientOpenFeign.findById(res.getIdClient());
                res.setClient(cl);
                ress.add(res);
            }
        }
        return ress;
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/stickets")
    public void ajouterSupportTicket(@RequestBody SupportTicket supportTicket) {
        supportTicket.setStatut("Opened");
        supportTicketRepository.save(supportTicket);
    }

    @PreAuthorize("hasAnyRole('ADMIN','CLIENT')")
    @GetMapping("/ticket_message/{id_ticket}")
    public List<TicketMessage> getTicketMessages(@PathVariable Long id_ticket) {
        SupportTicket st = supportTicketRepository.findById(id_ticket).get();
        return st.getMessages();
    }

    @PreAuthorize("hasAnyRole('ADMIN','CLIENT')")
    @PostMapping("/ticket_message/{id_ticket}")
    public void ajouterMessage(@PathVariable Long id_ticket, @RequestBody TicketMessage ticketMessage) {
        SupportTicket st = supportTicketRepository.findById(id_ticket).get();
        ticketMessage.setTicket(st);
        ticketMessageRepository.save(ticketMessage);
    }

    @PreAuthorize("hasAnyRole('ADMIN','CLIENT')")
    @DeleteMapping("/stickets/{id}")
    public ResponseEntity<?> supprimerTicket(@PathVariable Long id) {
        return supportTicketRepository.findById(id)
                .map(ticket -> {
                    supportTicketRepository.delete(ticket);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','CLIENT')")
    @DeleteMapping("/ticketMessage/{id}")
    public void supprimerTicketMessage(@PathVariable Long id) {
        ticketMessageRepository.delete(ticketMessageRepository.findById(id).get());
    }

    @PreAuthorize("hasAnyRole('ADMIN','CLIENT')")
    @PutMapping("/stickets/{id}")
    public void ticketSolved(@PathVariable Long id) {
        SupportTicket st = supportTicketRepository.findById(id).get();
        st.setStatut("Solved");
        supportTicketRepository.save(st);
    }

    @Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry;

    @GetMapping("/circuitbreaker-status")
    public String getCircuitBreakerStatus() {
        return circuitBreakerRegistry.circuitBreaker("supportServiceCB").getState().name();
    }



}
