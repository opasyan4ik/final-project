package com.example.finalproject.dto;

import com.example.finalproject.entity.PakStanislavCourseLevel;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Course create/update payload")
public class PakStanislavCourseRequestDto {

    @Schema(description = "Course title", example = "Java Backend from Zero")
    @NotBlank(message = "Course title is required")
    @Size(min = 3, max = 150, message = "Title must be between 3 and 150 characters")
    private String title;

    @Schema(description = "Course description", example = "Comprehensive Java and Spring Boot course")
    @NotBlank(message = "Course description is required")
    @Size(min = 10, max = 1000, message = "Description must be between 10 and 1000 characters")
    private String description;

    @Schema(description = "Course price", example = "49.99")
    @NotNull(message = "Course price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @Schema(description = "Duration in hours", example = "30")
    @NotNull(message = "Course duration is required")
    @Positive(message = "Duration must be greater than 0")
    private Integer durationInHours;

    @Schema(description = "Course level", example = "BEGINNER")
    @NotNull(message = "Course level is required")
    private PakStanislavCourseLevel level;

    @Schema(description = "Publication status", example = "true")
    private Boolean published;

    @Schema(description = "Category identifier", example = "2")
    @NotNull(message = "Category id is required")
    @Positive(message = "Category id must be greater than 0")
    private Long categoryId;
}
