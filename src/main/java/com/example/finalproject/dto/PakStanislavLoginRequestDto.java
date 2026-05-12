package com.example.finalproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Login request payload")
public class PakStanislavLoginRequestDto {

    @Schema(description = "User email", example = "stanislav@example.com")
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @Schema(description = "User password", example = "StrongPass123")
    @NotBlank(message = "Password is required")
    private String password;
}
