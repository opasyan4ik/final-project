package com.example.finalproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PakStanislavReviewRequestDto {

    private Integer rating;
    private String comment;
    private Long userId;
    private Long courseId;
}
