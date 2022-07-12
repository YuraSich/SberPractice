package com.example.cds.repository;

import com.example.cds.entitty.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRep extends JpaRepository<Content, String> {

}
