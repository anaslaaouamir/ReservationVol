package com.flight.supportclientmicroservice.repositories;

import com.flight.supportclientmicroservice.entities.TicketMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketMessageRepository extends JpaRepository<TicketMessage, Long> {
}
