package com.example.finalproject.service;

import com.example.finalproject.dto.PakStanislavCourseAnalyticsDto;
import org.springframework.scheduling.annotation.Async;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

public interface PakStanislavCourseAsyncService {

    @Async("pakStanislavTaskExecutor")
    CompletableFuture<BigDecimal> recalculateCourseRatingAsync(Long courseId);

    @Async("pakStanislavTaskExecutor")
    CompletableFuture<Long> countActiveEnrollmentsAsync(Long courseId);

    @Async("pakStanislavTaskExecutor")
    CompletableFuture<PakStanislavCourseAnalyticsDto> buildCourseAnalyticsAsync(Long courseId);
}
