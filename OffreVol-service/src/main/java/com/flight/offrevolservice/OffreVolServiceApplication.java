package com.flight.offrevolservice;

import com.flight.offrevolservice.entities.Vol;
import com.flight.offrevolservice.repositories.VolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication

@EnableFeignClients
public class OffreVolServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OffreVolServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner init(VolRepository volRepository) {
        return args -> {

            Vol vol3 = Vol.builder()
                    .villeDepart("Marrakech")
                    .villeDestination("Madrid")
                    .CompanyName("RAYAN Air")
                    .nameVol("GZ901")
                    .heureDepart(LocalDateTime.of(2025, 3, 20, 15, 0))
                    .heureArrivee(LocalDateTime.of(2025, 3, 20, 17, 30))
                    .prix(3200)
                    .placesDisponibles(180)
                    .statut("Retardé")
                    .build();

            Vol vol8 = Vol.builder()
                    .villeDepart("Marrakech")
                    .villeDestination("Madrid")
                    .CompanyName("RAYAN Air")
                    .nameVol("GZ991")
                    .heureDepart(LocalDateTime.of(2025, 4, 20, 15, 0))
                    .heureArrivee(LocalDateTime.of(2025, 4, 20, 17, 30))
                    .prix(3200)
                    .placesDisponibles(180)
                    .statut("Retardé")
                    .build();


            volRepository.save(vol3);volRepository.save(vol8);


            List<Vol> vols=volRepository.findAll();
            System.out.println("testing ");
            for(Vol vol:vols){
                System.out.println("de: "+ vol.getVilleDepart()+" à:"+vol.getVilleDestination());
            }
        };
    }
}
