package com.flight.clientservice;

import com.flight.clientservice.entities.Client;
import com.flight.clientservice.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
@EnableFeignClients

public class ClientServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientServiceApplication.class, args);
    }


    @Bean
    CommandLineRunner init(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            /*Client c1=Client.builder().nom("Anas").email("anas@gmail.com").telephone("0642900745").motPasse(passwordEncoder.encode("1234")).roles(List.of("ROLE_ADMIN")).build();
            Client c2=Client.builder().nom("Mohammed").email("mohammed@gmail.com").telephone("0642900745").build();
            Client c3=Client.builder().nom("Youssef").email("youssef@gmail.com").telephone("0642900745").build();

            clientRepository.save(c1);clientRepository.save(c2);clientRepository.save(c3);
*/
            List<Client> clients=clientRepository.findAll();
            System.out.println("testing ");
            for(Client c:clients){
                System.out.println("nom: "+c.getNom());
            }
        };
    }
}
