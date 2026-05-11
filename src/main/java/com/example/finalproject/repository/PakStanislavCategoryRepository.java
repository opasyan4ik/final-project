package com.example.finalproject.repository;

import com.example.finalproject.entity.PakStanislavCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PakStanislavCategoryRepository extends JpaRepository<PakStanislavCategory, Long> {

    boolean existsByName(String name);
}
