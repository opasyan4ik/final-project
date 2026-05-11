package com.example.finalproject.service.impl;

import com.example.finalproject.dto.PakStanislavCourseDto;
import com.example.finalproject.dto.PakStanislavCourseRequestDto;
import com.example.finalproject.entity.PakStanislavCategory;
import com.example.finalproject.entity.PakStanislavCourse;
import com.example.finalproject.exception.PakStanislavResourceNotFoundException;
import com.example.finalproject.mapper.PakStanislavCourseMapper;
import com.example.finalproject.repository.PakStanislavCategoryRepository;
import com.example.finalproject.repository.PakStanislavCourseRepository;
import com.example.finalproject.service.PakStanislavCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PakStanislavCourseServiceImpl implements PakStanislavCourseService {

    private final PakStanislavCourseRepository courseRepository;
    private final PakStanislavCategoryRepository categoryRepository;
    private final PakStanislavCourseMapper courseMapper;

    @Override
    @Transactional(readOnly = true)
    public List<PakStanislavCourseDto> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(courseMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PakStanislavCourseDto getCourseById(Long id) {
        PakStanislavCourse course = findCourseById(id);
        return courseMapper.toDto(course);
    }

    @Override
    @Transactional
    public PakStanislavCourseDto createCourse(PakStanislavCourseRequestDto requestDto) {
        PakStanislavCategory category = findCategoryById(requestDto.getCategoryId());
        PakStanislavCourse course = courseMapper.toEntity(requestDto, category);
        PakStanislavCourse savedCourse = courseRepository.save(course);
        return courseMapper.toDto(savedCourse);
    }

    @Override
    @Transactional
    public PakStanislavCourseDto updateCourse(Long id, PakStanislavCourseRequestDto requestDto) {
        PakStanislavCourse course = findCourseById(id);
        PakStanislavCategory category = findCategoryById(requestDto.getCategoryId());
        courseMapper.updateEntity(course, requestDto, category);
        PakStanislavCourse updatedCourse = courseRepository.save(course);
        return courseMapper.toDto(updatedCourse);
    }

    @Override
    @Transactional
    public void deleteCourse(Long id) {
        PakStanislavCourse course = findCourseById(id);
        courseRepository.delete(course);
    }

    private PakStanislavCourse findCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new PakStanislavResourceNotFoundException("Course not found with id: " + id));
    }

    private PakStanislavCategory findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new PakStanislavResourceNotFoundException("Category not found with id: " + id));
    }
}
