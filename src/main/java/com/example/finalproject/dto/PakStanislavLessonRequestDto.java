package com.example.finalproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PakStanislavLessonRequestDto {

    @NotBlank(message = "Lesson title is required")
    @Size(min = 3, max = 150, message = "Lesson title must be between 3 and 150 characters")
    private String title;

    @NotBlank(message = "Lesson content is required")
    @Size(min = 10, max = 2000, message = "Lesson content must be between 10 and 2000 characters")
    private String content;

    @NotNull(message = "Lesson order is required")
    @Positive(message = "Lesson order must be greater than 0")
    private Integer lessonOrder;

    @Size(max = 255, message = "Attachment path must not exceed 255 characters")
    private String attachmentPath;

    @NotNull(message = "Course id is required")
    @Positive(message = "Course id must be greater than 0")
    private Long courseId;
}
