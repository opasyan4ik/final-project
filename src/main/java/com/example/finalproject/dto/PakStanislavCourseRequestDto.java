package com.example.finalproject.dto;

import com.example.finalproject.entity.PakStanislavCourseLevel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PakStanislavCourseRequestDto {

    private String title;
    private String description;
    private BigDecimal price;
    private Integer durationInHours;
    private PakStanislavCourseLevel level;
    private Boolean published;
    private Long categoryId;
}
