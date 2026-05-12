package com.example.finalproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "Authentication response with JWT token")
public class PakStanislavAuthResponseDto {

    @Schema(description = "JWT access token")
    private String token;
    @Schema(description = "Token type", example = "Bearer")
    private String tokenType;
    @Schema(description = "User id", example = "1")
    private Long userId;
    @Schema(description = "User email", example = "stanislav@example.com")
    private String email;
    @Schema(description = "User full name", example = "Stanislav Pak")
    private String fullName;
}
