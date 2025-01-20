package com.flight.supportclientmicroservice.web;

import com.flight.supportclientmicroservice.entities.SupportTicket;
import com.flight.supportclientmicroservice.entities.TicketMessage;
import com.flight.supportclientmicroservice.models.Client;
import com.flight.supportclientmicroservice.repositories.SupportTicketRepository;
import com.flight.supportclientmicroservice.repositories.TicketMessageRepository;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/stickets/{id}")
    public SupportTicket uneRservation(@PathVariable Long id) {
        SupportTicket res = supportTicketRepository.findById(id).get();
        Client cl = clientOpenFeign.findById(res.getIdClient());
        res.setClient(cl);
        return res;
    }

    @PostMapping("/stickets")
    public void ajouterSupportTicket(@RequestBody SupportTicket supportTicket) {
        supportTicket.setStatut("Opened");
        supportTicketRepository.save(supportTicket);
    }

    @GetMapping("/ticket_message/{id_ticket}")
    public List<TicketMessage> getTicketMessages(@PathVariable Long id_ticket) {
        SupportTicket st = supportTicketRepository.findById(id_ticket).get();
        return st.getMessages();
    }


    @PostMapping("/ticket_message/{id_ticket}")
    public void ajouterMessage(@PathVariable Long id_ticket, @RequestBody TicketMessage ticketMessage) {
        SupportTicket st = supportTicketRepository.findById(id_ticket).get();
        ticketMessage.setTicket(st);
        ticketMessageRepository.save(ticketMessage);
    }

    @DeleteMapping("/stickets/{id}")
    public void supprimerTicket(@PathVariable Long id) {
        SupportTicket st = supportTicketRepository.findById(id).get();
        supportTicketRepository.delete(st);
    }

    @DeleteMapping("/ticketMessage/{id}")
    public void supprimerTicketMessage(@PathVariable Long id) {
        ticketMessageRepository.delete(ticketMessageRepository.findById(id).get());
    }

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
