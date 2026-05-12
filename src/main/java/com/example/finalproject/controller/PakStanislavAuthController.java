package com.example.finalproject.controller;

import com.example.finalproject.dto.PakStanislavAuthResponseDto;
import com.example.finalproject.dto.PakStanislavLoginRequestDto;
import com.example.finalproject.dto.PakStanislavRegisterRequestDto;
import com.example.finalproject.dto.PakStanislavUserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.finalproject.service.PakStanislavAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user registration and login")
public class PakStanislavAuthController {

    private final PakStanislavAuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error or duplicate email")
    })
    public ResponseEntity<PakStanislavUserDto> register(
            @Valid @RequestBody PakStanislavRegisterRequestDto requestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(requestDto));
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user and return JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    public ResponseEntity<PakStanislavAuthResponseDto> login(
            @Valid @RequestBody PakStanislavLoginRequestDto requestDto
    ) {
        return ResponseEntity.ok(authService.login(requestDto));
    }
}
