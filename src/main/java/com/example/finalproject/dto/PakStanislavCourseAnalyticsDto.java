package com.example.finalproject.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class PakStanislavCourseAnalyticsDto {

    private Long courseId;
    private BigDecimal averageRating;
    private Long reviewCount;
    private Long activeEnrollments;
}
