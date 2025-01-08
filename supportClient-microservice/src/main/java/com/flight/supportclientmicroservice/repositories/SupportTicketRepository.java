package com.flight.supportclientmicroservice.repositories;

import com.flight.supportclientmicroservice.entities.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long> {
}
