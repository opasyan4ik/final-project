package com.example.finalproject.dto;

import com.example.finalproject.entity.PakStanislavEnrollmentStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PakStanislavEnrollmentRequestDto {

    @NotNull(message = "Enrollment status is required")
    private PakStanislavEnrollmentStatus status;

    @NotNull(message = "User id is required")
    @Positive(message = "User id must be greater than 0")
    private Long userId;

    @NotNull(message = "Course id is required")
    @Positive(message = "Course id must be greater than 0")
    private Long courseId;
}
