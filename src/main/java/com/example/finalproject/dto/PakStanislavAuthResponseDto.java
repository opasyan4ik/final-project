package com.example.finalproject.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PakStanislavAuthResponseDto {

    private String token;
    private String tokenType;
    private Long userId;
    private String email;
    private String fullName;
}
