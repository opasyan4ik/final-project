package com.example.finalproject.service;

import com.example.finalproject.dto.PakStanislavCourseDto;
import com.example.finalproject.dto.PakStanislavCourseRequestDto;

import java.util.List;

public interface PakStanislavCourseService {

    List<PakStanislavCourseDto> getAllCourses();

    PakStanislavCourseDto getCourseById(Long id);

    PakStanislavCourseDto createCourse(PakStanislavCourseRequestDto requestDto);

    PakStanislavCourseDto updateCourse(Long id, PakStanislavCourseRequestDto requestDto);

    void deleteCourse(Long id);
}
