package com.example.finalproject.repository;

import com.example.finalproject.entity.PakStanislavUser;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PakStanislavUserRepository extends JpaRepository<PakStanislavUser, Long> {

    @EntityGraph(attributePaths = "roles")
    Optional<PakStanislavUser> findByEmail(String email);

    boolean existsByEmail(String email);
}
