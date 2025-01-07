package com.flight.offrevolservice;

import com.flight.offrevolservice.entities.Vol;
import com.flight.offrevolservice.repositories.VolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class OffreVolServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OffreVolServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner init(VolRepository volRepository) {
        return args -> {
            Vol vol1=Vol.builder().villeDepart("Salé").villeDestination("Mecca").heureDepart(LocalDateTime.of(2025, 1, 10, 8, 0)).heureArrivee(LocalDateTime.of(2025, 1, 10, 14, 15)).prix(6000).placesDisponibles(200).statut("Programmé").build();
            Vol vol2 = Vol.builder()
                    .villeDepart("Casablanca")
                    .villeDestination("Paris")
                    .heureDepart(LocalDateTime.of(2025, 2, 5, 9, 30))
                    .heureArrivee(LocalDateTime.of(2025, 2, 5, 13, 45))
                    .prix(4500)
                    .placesDisponibles(150)
                    .statut("Programmé")
                    .build();

            Vol vol3 = Vol.builder()
                    .villeDepart("Marrakech")
                    .villeDestination("Madrid")
                    .heureDepart(LocalDateTime.of(2025, 3, 20, 15, 0))
                    .heureArrivee(LocalDateTime.of(2025, 3, 20, 17, 30))
                    .prix(3200)
                    .placesDisponibles(180)
                    .statut("Retardé")
                    .build();

            Vol vol4 = Vol.builder()
                    .villeDepart("Rabat")
                    .villeDestination("Istanbul")
                    .heureDepart(LocalDateTime.of(2025, 4, 10, 6, 45))
                    .heureArrivee(LocalDateTime.of(2025, 4, 10, 12, 0))
                    .prix(7000)
                    .placesDisponibles(100)
                    .statut("Annulé")
                    .build();


            volRepository.save(vol1);volRepository.save(vol2);
            volRepository.save(vol4);volRepository.save(vol3);

            List<Vol> vols=volRepository.findAll();
            System.out.println("testing ");
            for(Vol vol:vols){
                System.out.println("de: "+ vol.getVilleDepart()+" à:"+vol.getVilleDestination());
            }
        };
    }
}
