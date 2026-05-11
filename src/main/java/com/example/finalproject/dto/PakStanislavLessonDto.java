package com.example.finalproject.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PakStanislavLessonDto {

    private Long id;
    private String title;
    private String content;
    private Integer lessonOrder;
    private String attachmentPath;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long courseId;
    private String courseTitle;
}
