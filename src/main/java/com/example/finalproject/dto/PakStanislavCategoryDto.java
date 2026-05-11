package com.example.finalproject.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PakStanislavCategoryDto {

    private Long id;
    private String name;
    private String description;
    private int courseCount;
}
