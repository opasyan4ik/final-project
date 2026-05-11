package com.example.finalproject.repository;

import com.example.finalproject.entity.PakStanislavRole;
import com.example.finalproject.entity.PakStanislavRoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PakStanislavRoleRepository extends JpaRepository<PakStanislavRole, Long> {

    Optional<PakStanislavRole> findByName(PakStanislavRoleType name);

    boolean existsByName(PakStanislavRoleType name);
}
