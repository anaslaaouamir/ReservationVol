package com.flight.supportclientmicroservice.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class TicketMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMessage;

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    @JsonBackReference
    private SupportTicket ticket; // Linked to the SupportTicket

    private String sender; // "Client" or "Admin" to identify who sent the message

    private String content; // Message content

    private LocalDateTime timestamp; // Timestamp of the message
}
