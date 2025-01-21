package com.flight.clientservice.web;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "SUPPORTCLIENT-SERVICE")
public interface SupportTicketOpenFeign {

    @DeleteMapping("/stickets/{id}")
    public void supprimerTicket(@PathVariable Long id) ;

}
