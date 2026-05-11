package com.example.finalproject.repository;

import com.example.finalproject.entity.PakStanislavLesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PakStanislavLessonRepository extends JpaRepository<PakStanislavLesson, Long> {

    List<PakStanislavLesson> findByCourseIdOrderByLessonOrderAsc(Long courseId);
}
