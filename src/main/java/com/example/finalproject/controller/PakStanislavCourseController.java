package com.example.finalproject.controller;

import com.example.finalproject.dto.PakStanislavCourseDto;
import com.example.finalproject.dto.PakStanislavCourseRequestDto;
import com.example.finalproject.entity.PakStanislavCourseLevel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.finalproject.service.PakStanislavCourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Tag(name = "Courses", description = "CRUD and advanced querying for courses")
public class PakStanislavCourseController {

    private final PakStanislavCourseService courseService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER','STUDENT')")
    @Operation(summary = "Get courses with pagination, sorting and filtering")
    public ResponseEntity<Page<PakStanislavCourseDto>> getCourses(
            @Parameter(description = "Page number, starts with 0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort field, e.g. createdAt, price, title")
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Sort direction: asc or desc")
            @RequestParam(defaultValue = "desc") String sortDir,
            @Parameter(description = "Keyword for title/description search")
            @RequestParam(required = false) String keyword,
            @Parameter(description = "Course level filter")
            @RequestParam(required = false) PakStanislavCourseLevel level,
            @Parameter(description = "Published flag filter")
            @RequestParam(required = false) Boolean published,
            @Parameter(description = "Category id filter")
            @RequestParam(required = false) Long categoryId,
            @Parameter(description = "Minimum price")
            @RequestParam(required = false) BigDecimal minPrice,
            @Parameter(description = "Maximum price")
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
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER','STUDENT')")
    @Operation(summary = "Get course by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course found"),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
    public ResponseEntity<PakStanislavCourseDto> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    @Operation(summary = "Create course")
    @ApiResponse(responseCode = "201", description = "Course created")
    public ResponseEntity<PakStanislavCourseDto> createCourse(
            @Valid @RequestBody PakStanislavCourseRequestDto requestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.createCourse(requestDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    @Operation(summary = "Update course")
    public ResponseEntity<PakStanislavCourseDto> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody PakStanislavCourseRequestDto requestDto
    ) {
        return ResponseEntity.ok(courseService.updateCourse(id, requestDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete course")
    @ApiResponse(responseCode = "204", description = "Course deleted")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
