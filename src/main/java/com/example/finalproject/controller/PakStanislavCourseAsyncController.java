package com.example.finalproject.controller;

import com.example.finalproject.dto.PakStanislavCourseAnalyticsDto;
import com.example.finalproject.service.PakStanislavCourseAsyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/courses/{courseId}/async")
@RequiredArgsConstructor
public class PakStanislavCourseAsyncController {

    private final PakStanislavCourseAsyncService courseAsyncService;

    @GetMapping("/recalculate-rating")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public CompletableFuture<ResponseEntity<Map<String, Object>>> recalculateRating(
            @PathVariable Long courseId
    ) {
        return courseAsyncService.recalculateCourseRatingAsync(courseId)
                .thenApply(rating -> ResponseEntity.ok(Map.of(
                        "courseId", courseId,
                        "averageRating", rating
                )));
    }

    @GetMapping("/active-enrollments")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public CompletableFuture<ResponseEntity<Map<String, Object>>> activeEnrollments(
            @PathVariable Long courseId
    ) {
        return courseAsyncService.countActiveEnrollmentsAsync(courseId)
                .thenApply(count -> ResponseEntity.ok(Map.of(
                        "courseId", courseId,
                        "activeEnrollments", count
                )));
    }

    @GetMapping("/analytics")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public CompletableFuture<ResponseEntity<PakStanislavCourseAnalyticsDto>> analytics(
            @PathVariable Long courseId
    ) {
        return courseAsyncService.buildCourseAnalyticsAsync(courseId)
                .thenApply(ResponseEntity::ok);
    }
}
