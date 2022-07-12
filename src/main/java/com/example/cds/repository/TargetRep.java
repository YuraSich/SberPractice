package com.example.cds.repository;

import com.example.cds.entitty.Target;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TargetRep extends JpaRepository<Target, String> {
    Optional<Target> findByGuid(String guid);
}
