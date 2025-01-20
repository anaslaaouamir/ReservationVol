package com.flight.clientservice.repositories;

import com.flight.clientservice.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT c FROM Client c WHERE " +
            "(:recherche IS NULL OR LOWER(c.nom) LIKE LOWER(CONCAT('%', :recherche, '%')) OR " +
            "LOWER(c.email) LIKE LOWER(CONCAT('%', :recherche, '%')) OR " +
            "LOWER(c.motPasse) LIKE LOWER(CONCAT('%', :recherche, '%')) OR " +
            "LOWER(c.telephone) LIKE LOWER(CONCAT('%', :recherche, '%')))")
    List<Client> searchClients(@Param("recherche") String recherche);

}
