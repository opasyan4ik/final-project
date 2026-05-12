package com.example.finalproject.controller;

import com.example.finalproject.dto.PakStanislavAuthResponseDto;
import com.example.finalproject.dto.PakStanislavLoginRequestDto;
import com.example.finalproject.dto.PakStanislavRegisterRequestDto;
import com.example.finalproject.dto.PakStanislavUserDto;
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
public class PakStanislavAuthController {

    private final PakStanislavAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<PakStanislavUserDto> register(
            @Valid @RequestBody PakStanislavRegisterRequestDto requestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(requestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<PakStanislavAuthResponseDto> login(
            @Valid @RequestBody PakStanislavLoginRequestDto requestDto
    ) {
        return ResponseEntity.ok(authService.login(requestDto));
    }
}
