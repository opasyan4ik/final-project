package com.example.finalproject.mapper;

import com.example.finalproject.dto.PakStanislavCourseDto;
import com.example.finalproject.dto.PakStanislavCourseRequestDto;
import com.example.finalproject.entity.PakStanislavCategory;
import com.example.finalproject.entity.PakStanislavCourse;
import org.springframework.stereotype.Component;

@Component
public class PakStanislavCourseMapper {

    public PakStanislavCourseDto toDto(PakStanislavCourse course) {
        return PakStanislavCourseDto.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .price(course.getPrice())
                .durationInHours(course.getDurationInHours())
                .level(course.getLevel())
                .published(course.getPublished())
                .averageRating(course.getAverageRating())
                .reviewCount(course.getReviewCount())
                .createdAt(course.getCreatedAt())
                .updatedAt(course.getUpdatedAt())
                .categoryId(course.getCategory().getId())
                .categoryName(course.getCategory().getName())
                .build();
    }

    public PakStanislavCourse toEntity(PakStanislavCourseRequestDto requestDto, PakStanislavCategory category) {
        return PakStanislavCourse.builder()
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .price(requestDto.getPrice())
                .durationInHours(requestDto.getDurationInHours())
                .level(requestDto.getLevel())
                .published(requestDto.getPublished() != null ? requestDto.getPublished() : Boolean.FALSE)
                .category(category)
                .build();
    }

    public void updateEntity(PakStanislavCourse course, PakStanislavCourseRequestDto requestDto, PakStanislavCategory category) {
        course.setTitle(requestDto.getTitle());
        course.setDescription(requestDto.getDescription());
        course.setPrice(requestDto.getPrice());
        course.setDurationInHours(requestDto.getDurationInHours());
        course.setLevel(requestDto.getLevel());
        course.setPublished(requestDto.getPublished() != null ? requestDto.getPublished() : course.getPublished());
        course.setCategory(category);
    }
}
