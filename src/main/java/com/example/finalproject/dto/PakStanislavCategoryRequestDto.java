package com.example.finalproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Category create/update payload")
public class PakStanislavCategoryRequestDto {

    @Schema(description = "Category name", example = "Programming")
    @NotBlank(message = "Category name is required")
    @Size(min = 2, max = 100, message = "Category name must be between 2 and 100 characters")
    private String name;

    @Schema(description = "Category description", example = "All software development courses")
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;
}
