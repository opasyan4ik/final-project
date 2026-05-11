package com.example.finalproject.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PakStanislavReviewDto {

    private Long id;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;
    private String userFullName;
    private Long courseId;
    private String courseTitle;
}
