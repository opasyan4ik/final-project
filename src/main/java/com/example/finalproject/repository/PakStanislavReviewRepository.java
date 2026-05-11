package com.example.finalproject.repository;

import com.example.finalproject.entity.PakStanislavReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PakStanislavReviewRepository extends JpaRepository<PakStanislavReview, Long> {

    List<PakStanislavReview> findByCourseId(Long courseId);

    List<PakStanislavReview> findByUserId(Long userId);
}
