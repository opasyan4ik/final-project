package com.example.finalproject.dto;

import com.example.finalproject.entity.PakStanislavCourseLevel;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class PakStanislavCourseDto {

    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private Integer durationInHours;
    private PakStanislavCourseLevel level;
    private Boolean published;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long categoryId;
    private String categoryName;
}
