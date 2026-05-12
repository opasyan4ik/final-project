package com.example.finalproject.dto;

import com.example.finalproject.entity.PakStanislavRoleType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Registration request payload")
public class PakStanislavRegisterRequestDto {

    @Schema(description = "User first name", example = "Stanislav")
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 100, message = "First name must be between 2 and 100 characters")
    private String firstName;

    @Schema(description = "User last name", example = "Pak")
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 100, message = "Last name must be between 2 and 100 characters")
    private String lastName;

    @Schema(description = "User email", example = "stanislav@example.com")
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @Schema(description = "User password", example = "StrongPass123")
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 255, message = "Password must be between 6 and 255 characters")
    private String password;

    @Schema(description = "Initial role", example = "ROLE_STUDENT")
    @NotNull(message = "Role is required")
    private PakStanislavRoleType role;
}
