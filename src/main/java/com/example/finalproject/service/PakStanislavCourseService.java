package com.example.finalproject.service;

import com.example.finalproject.dto.PakStanislavCourseDto;
import com.example.finalproject.dto.PakStanislavCourseRequestDto;
import com.example.finalproject.entity.PakStanislavCourseLevel;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public interface PakStanislavCourseService {

    Page<PakStanislavCourseDto> getCourses(
            int page,
            int size,
            String sortBy,
            String sortDir,
            String keyword,
            PakStanislavCourseLevel level,
            Boolean published,
            Long categoryId,
            BigDecimal minPrice,
            BigDecimal maxPrice
    );

    PakStanislavCourseDto getCourseById(Long id);

    PakStanislavCourseDto createCourse(PakStanislavCourseRequestDto requestDto);

    PakStanislavCourseDto updateCourse(Long id, PakStanislavCourseRequestDto requestDto);

    void deleteCourse(Long id);
}
