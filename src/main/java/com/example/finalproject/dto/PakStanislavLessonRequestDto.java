package com.example.finalproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PakStanislavLessonRequestDto {

    private String title;
    private String content;
    private Integer lessonOrder;
    private String attachmentPath;
    private Long courseId;
}
