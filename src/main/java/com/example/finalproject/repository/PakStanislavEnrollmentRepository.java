package com.example.finalproject.repository;

import com.example.finalproject.entity.PakStanislavEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PakStanislavEnrollmentRepository extends JpaRepository<PakStanislavEnrollment, Long> {

    List<PakStanislavEnrollment> findByUserId(Long userId);

    List<PakStanislavEnrollment> findByCourseId(Long courseId);

    Optional<PakStanislavEnrollment> findByUserIdAndCourseId(Long userId, Long courseId);
}
