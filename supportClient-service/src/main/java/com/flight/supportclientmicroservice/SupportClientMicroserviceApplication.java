package com.flight.supportclientmicroservice;

import com.flight.supportclientmicroservice.entities.SupportTicket;
import com.flight.supportclientmicroservice.entities.TicketMessage;
import com.flight.supportclientmicroservice.repositories.SupportTicketRepository;
import com.flight.supportclientmicroservice.repositories.TicketMessageRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableFeignClients

public class SupportClientMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SupportClientMicroserviceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(SupportTicketRepository supportTicketRepository, TicketMessageRepository ticketMessageRepository) {
        return args -> {
            SupportTicket st=SupportTicket.builder()
                    .sujet("Demande de refund")
                    .description("xxxxxxxxxxxxxxxxxxxxxxxxxxx")
                    .statut("Opened")
                    .dateCreation(LocalDateTime.now())
                    .dateMiseAJour(LocalDateTime.now())
                    .idClient(1L)
                    .build();

            supportTicketRepository.save(st);


            TicketMessage tm=TicketMessage.builder()
                    .ticket(st)
                    .sender("Admin")
                    .content("Pouquoi?")
                    .build();
            ticketMessageRepository.save(tm);

            TicketMessage tm1=TicketMessage.builder()
                    .ticket(st)
                    .sender("Client")
                    .content("Urgence médicale")
                    .build();
            ticketMessageRepository.save(tm1);
        };
    }

}
