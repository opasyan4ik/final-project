package com.example.finalproject.service.impl;

import com.example.finalproject.dto.PakStanislavCourseAnalyticsDto;
import com.example.finalproject.entity.PakStanislavCourse;
import com.example.finalproject.entity.PakStanislavEnrollmentStatus;
import com.example.finalproject.exception.PakStanislavResourceNotFoundException;
import com.example.finalproject.repository.PakStanislavCourseRepository;
import com.example.finalproject.repository.PakStanislavEnrollmentRepository;
import com.example.finalproject.repository.PakStanislavReviewRepository;
import com.example.finalproject.service.PakStanislavCourseAsyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class PakStanislavCourseAsyncServiceImpl implements PakStanislavCourseAsyncService {

    private final PakStanislavCourseRepository courseRepository;
    private final PakStanislavReviewRepository reviewRepository;
    private final PakStanislavEnrollmentRepository enrollmentRepository;

    @Override
    @Async("pakStanislavTaskExecutor")
    @Transactional
    public CompletableFuture<BigDecimal> recalculateCourseRatingAsync(Long courseId) {
        PakStanislavCourse course = findCourseById(courseId);

        long reviewCount = reviewRepository.countByCourseId(courseId);
        Double average = reviewRepository.findAverageRatingByCourseId(courseId).orElse(0.0);
        BigDecimal averageRating = BigDecimal.valueOf(average).setScale(2, RoundingMode.HALF_UP);

        course.setReviewCount((int) reviewCount);
        course.setAverageRating(averageRating);
        courseRepository.save(course);

        return CompletableFuture.completedFuture(averageRating);
    }

    @Override
    @Async("pakStanislavTaskExecutor")
    @Transactional(readOnly = true)
    public CompletableFuture<Long> countActiveEnrollmentsAsync(Long courseId) {
        findCourseById(courseId);
        long activeCount = enrollmentRepository.countByCourseIdAndStatus(courseId, PakStanislavEnrollmentStatus.ACTIVE);
        return CompletableFuture.completedFuture(activeCount);
    }

    @Override
    @Async("pakStanislavTaskExecutor")
    public CompletableFuture<PakStanislavCourseAnalyticsDto> buildCourseAnalyticsAsync(Long courseId) {
        CompletableFuture<BigDecimal> ratingFuture = recalculateCourseRatingAsync(courseId);
        CompletableFuture<Long> activeFuture = countActiveEnrollmentsAsync(courseId);

        return ratingFuture.thenCombine(activeFuture, (averageRating, activeEnrollments) -> {
            long reviewCount = reviewRepository.countByCourseId(courseId);
            return PakStanislavCourseAnalyticsDto.builder()
                    .courseId(courseId)
                    .averageRating(averageRating)
                    .reviewCount(reviewCount)
                    .activeEnrollments(activeEnrollments)
                    .build();
        });
    }

    private PakStanislavCourse findCourseById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new PakStanislavResourceNotFoundException("Course not found with id: " + courseId));
    }
}
