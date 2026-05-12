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
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class PakStanislavCourseAsyncServiceImpl implements PakStanislavCourseAsyncService {

    private final PakStanislavCourseRepository courseRepository;
    private final PakStanislavReviewRepository reviewRepository;
    private final PakStanislavEnrollmentRepository enrollmentRepository;

    @Override
    @Async("pakStanislavTaskExecutor")
    @Transactional
    public CompletableFuture<BigDecimal> recalculateCourseRatingAsync(Long courseId) {
        log.info("Async rating recalculation started for courseId={}", courseId);
        PakStanislavCourse course = findCourseById(courseId);

        long reviewCount = reviewRepository.countByCourseId(courseId);
        Double average = reviewRepository.findAverageRatingByCourseId(courseId).orElse(0.0);
        BigDecimal averageRating = BigDecimal.valueOf(average).setScale(2, RoundingMode.HALF_UP);

        course.setReviewCount((int) reviewCount);
        course.setAverageRating(averageRating);
        courseRepository.save(course);
        log.info("Async rating recalculation finished for courseId={} averageRating={} reviewCount={}",
                courseId, averageRating, reviewCount);

        return CompletableFuture.completedFuture(averageRating);
    }

    @Override
    @Async("pakStanislavTaskExecutor")
    @Transactional(readOnly = true)
    public CompletableFuture<Long> countActiveEnrollmentsAsync(Long courseId) {
        log.info("Async active enrollments count started for courseId={}", courseId);
        findCourseById(courseId);
        long activeCount = enrollmentRepository.countByCourseIdAndStatus(courseId, PakStanislavEnrollmentStatus.ACTIVE);
        log.info("Async active enrollments count finished for courseId={} activeCount={}", courseId, activeCount);
        return CompletableFuture.completedFuture(activeCount);
    }

    @Override
    @Async("pakStanislavTaskExecutor")
    public CompletableFuture<PakStanislavCourseAnalyticsDto> buildCourseAnalyticsAsync(Long courseId) {
        log.info("Async analytics build started for courseId={}", courseId);
        CompletableFuture<BigDecimal> ratingFuture = recalculateCourseRatingAsync(courseId);
        CompletableFuture<Long> activeFuture = countActiveEnrollmentsAsync(courseId);

        return ratingFuture.thenCombine(activeFuture, (averageRating, activeEnrollments) -> {
            long reviewCount = reviewRepository.countByCourseId(courseId);
            log.info("Async analytics build finished for courseId={} averageRating={} activeEnrollments={} reviewCount={}",
                    courseId, averageRating, activeEnrollments, reviewCount);
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
