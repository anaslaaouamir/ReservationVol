package com.flight.clientservice.web;

import com.flight.clientservice.security.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "SUPPORTCLIENT-SERVICE", configuration = FeignClientConfig.class)
public interface SupportTicketOpenFeign {

    @DeleteMapping("/stickets/{id}")
    ResponseEntity<?> supprimerTicket(@PathVariable Long id);

}


