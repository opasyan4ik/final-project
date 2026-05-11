package com.example.finalproject.mapper;

import com.example.finalproject.dto.PakStanislavLessonDto;
import com.example.finalproject.dto.PakStanislavLessonRequestDto;
import com.example.finalproject.entity.PakStanislavCourse;
import com.example.finalproject.entity.PakStanislavLesson;
import org.springframework.stereotype.Component;

@Component
public class PakStanislavLessonMapper {

    public PakStanislavLessonDto toDto(PakStanislavLesson lesson) {
        return PakStanislavLessonDto.builder()
                .id(lesson.getId())
                .title(lesson.getTitle())
                .content(lesson.getContent())
                .lessonOrder(lesson.getLessonOrder())
                .attachmentPath(lesson.getAttachmentPath())
                .createdAt(lesson.getCreatedAt())
                .updatedAt(lesson.getUpdatedAt())
                .courseId(lesson.getCourse().getId())
                .courseTitle(lesson.getCourse().getTitle())
                .build();
    }

    public PakStanislavLesson toEntity(PakStanislavLessonRequestDto requestDto, PakStanislavCourse course) {
        return PakStanislavLesson.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .lessonOrder(requestDto.getLessonOrder())
                .attachmentPath(requestDto.getAttachmentPath())
                .course(course)
                .build();
    }

    public void updateEntity(PakStanislavLesson lesson, PakStanislavLessonRequestDto requestDto, PakStanislavCourse course) {
        lesson.setTitle(requestDto.getTitle());
        lesson.setContent(requestDto.getContent());
        lesson.setLessonOrder(requestDto.getLessonOrder());
        lesson.setAttachmentPath(requestDto.getAttachmentPath());
        lesson.setCourse(course);
    }
}
