package com.example.finalproject.dto;

import com.example.finalproject.entity.PakStanislavCourseLevel;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PakStanislavCourseRequestDto {

    @NotBlank(message = "Course title is required")
    @Size(min = 3, max = 150, message = "Title must be between 3 and 150 characters")
    private String title;

    @NotBlank(message = "Course description is required")
    @Size(min = 10, max = 1000, message = "Description must be between 10 and 1000 characters")
    private String description;

    @NotNull(message = "Course price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "Course duration is required")
    @Positive(message = "Duration must be greater than 0")
    private Integer durationInHours;

    @NotNull(message = "Course level is required")
    private PakStanislavCourseLevel level;

    private Boolean published;

    @NotNull(message = "Category id is required")
    @Positive(message = "Category id must be greater than 0")
    private Long categoryId;
}
