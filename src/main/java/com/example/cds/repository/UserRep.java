package com.example.cds.repository;


import com.example.cds.entitty.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRep extends JpaRepository<Users, String> {
    Optional<Users> findByGuid(String str);
}
