package com.flight.offrevolservice.repositories;

import com.flight.offrevolservice.entities.Vol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolRepository extends JpaRepository<Vol, Long> {
}
