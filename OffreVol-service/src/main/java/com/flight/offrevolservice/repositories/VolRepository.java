package com.flight.offrevolservice.repositories;

import com.flight.offrevolservice.entities.Vol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface VolRepository extends JpaRepository<Vol, Long> {

    List<Vol> findByVilleDepartAndVilleDestinationAndHeureDepartBetween(
            String villeDepart,
            String villeDestination,
            LocalDateTime startOfDay,
            LocalDateTime endOfDay
    );

    @Query("SELECT v FROM Vol v " +
            "WHERE (:villeDepart IS NULL OR v.villeDepart = :villeDepart) " +
            "AND (:villeDestination IS NULL OR v.villeDestination = :villeDestination) " +
            "AND (:startOfDay IS NULL OR :endOfDay IS NULL OR v.heureDepart BETWEEN :startOfDay AND :endOfDay)")
    List<Vol> searchVols(
            @Param("villeDepart") String villeDepart,
            @Param("villeDestination") String villeDestination,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );
}
