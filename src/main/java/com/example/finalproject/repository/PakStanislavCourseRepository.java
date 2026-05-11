package com.example.finalproject.repository;

import com.example.finalproject.entity.PakStanislavCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PakStanislavCourseRepository extends JpaRepository<PakStanislavCourse, Long>,
        JpaSpecificationExecutor<PakStanislavCourse> {
}
