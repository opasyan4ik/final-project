package com.example.finalproject.mapper;

import com.example.finalproject.dto.PakStanislavReviewDto;
import com.example.finalproject.dto.PakStanislavReviewRequestDto;
import com.example.finalproject.entity.PakStanislavCourse;
import com.example.finalproject.entity.PakStanislavReview;
import com.example.finalproject.entity.PakStanislavUser;
import org.springframework.stereotype.Component;

@Component
public class PakStanislavReviewMapper {

    public PakStanislavReviewDto toDto(PakStanislavReview review) {
        return PakStanislavReviewDto.builder()
                .id(review.getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .userId(review.getUser().getId())
                .userFullName(review.getUser().getFirstName() + " " + review.getUser().getLastName())
                .courseId(review.getCourse().getId())
                .courseTitle(review.getCourse().getTitle())
                .build();
    }

    public PakStanislavReview toEntity(PakStanislavReviewRequestDto requestDto,
                                       PakStanislavUser user,
                                       PakStanislavCourse course) {
        return PakStanislavReview.builder()
                .rating(requestDto.getRating())
                .comment(requestDto.getComment())
                .user(user)
                .course(course)
                .build();
    }

    public void updateEntity(PakStanislavReview review,
                             PakStanislavReviewRequestDto requestDto,
                             PakStanislavUser user,
                             PakStanislavCourse course) {
        review.setRating(requestDto.getRating());
        review.setComment(requestDto.getComment());
        review.setUser(user);
        review.setCourse(course);
    }
}
