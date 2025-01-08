package com.flight.supportclientmicroservice.web;

import com.flight.supportclientmicroservice.entities.SupportTicket;
import com.flight.supportclientmicroservice.models.Client;
import com.flight.supportclientmicroservice.repositories.SupportTicketRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController

public class SupportTicketController {

    public SupportTicketController(SupportTicketRepository supportTicketRepository,ClientOpenFeign clientOpenFeign) {
        this.clientOpenFeign = clientOpenFeign;
        this.supportTicketRepository = supportTicketRepository;
    }

    private ClientOpenFeign clientOpenFeign;
    private SupportTicketRepository supportTicketRepository;

    @GetMapping("/stickets")
    public List<SupportTicket> allSupportTickets(){
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

}
