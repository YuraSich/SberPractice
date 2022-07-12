package com.example.cds.repository;

import com.example.cds.entitty.Viewed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ViewedRep extends JpaRepository<Viewed, String> {
    List<Viewed> findByIsSendedFalse();
}
