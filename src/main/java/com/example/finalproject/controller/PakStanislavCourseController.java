package com.example.finalproject.controller;

import com.example.finalproject.dto.PakStanislavCourseDto;
import com.example.finalproject.dto.PakStanislavCourseRequestDto;
import com.example.finalproject.entity.PakStanislavCourseLevel;
import com.example.finalproject.service.PakStanislavCourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class PakStanislavCourseController {

    private final PakStanislavCourseService courseService;

    @GetMapping
    public ResponseEntity<Page<PakStanislavCourseDto>> getCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) PakStanislavCourseLevel level,
            @RequestParam(required = false) Boolean published,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice
    ) {
        return ResponseEntity.ok(courseService.getCourses(
                page,
                size,
                sortBy,
                sortDir,
                keyword,
                level,
                published,
                categoryId,
                minPrice,
                maxPrice
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PakStanislavCourseDto> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PostMapping
    public ResponseEntity<PakStanislavCourseDto> createCourse(
            @Valid @RequestBody PakStanislavCourseRequestDto requestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.createCourse(requestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PakStanislavCourseDto> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody PakStanislavCourseRequestDto requestDto
    ) {
        return ResponseEntity.ok(courseService.updateCourse(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
