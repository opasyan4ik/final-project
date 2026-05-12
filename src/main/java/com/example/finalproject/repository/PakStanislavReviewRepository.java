package com.example.finalproject.repository;

import com.example.finalproject.entity.PakStanislavReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PakStanislavReviewRepository extends JpaRepository<PakStanislavReview, Long> {

    List<PakStanislavReview> findByCourseId(Long courseId);

    List<PakStanislavReview> findByUserId(Long userId);

    long countByCourseId(Long courseId);

    @Query("select avg(r.rating) from PakStanislavReview r where r.course.id = :courseId")
    Optional<Double> findAverageRatingByCourseId(Long courseId);
}
