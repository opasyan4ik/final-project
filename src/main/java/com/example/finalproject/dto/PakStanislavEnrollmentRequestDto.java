package com.example.finalproject.dto;

import com.example.finalproject.entity.PakStanislavEnrollmentStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PakStanislavEnrollmentRequestDto {

    private PakStanislavEnrollmentStatus status;
    private Long userId;
    private Long courseId;
}
