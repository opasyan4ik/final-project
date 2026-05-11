package com.example.finalproject.dto;

import com.example.finalproject.entity.PakStanislavEnrollmentStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PakStanislavEnrollmentDto {

    private Long id;
    private PakStanislavEnrollmentStatus status;
    private LocalDateTime enrolledAt;
    private Long userId;
    private String userFullName;
    private Long courseId;
    private String courseTitle;
}
