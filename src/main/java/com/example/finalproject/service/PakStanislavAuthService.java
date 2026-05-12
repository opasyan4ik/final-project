package com.example.finalproject.service;

import com.example.finalproject.dto.PakStanislavAuthResponseDto;
import com.example.finalproject.dto.PakStanislavLoginRequestDto;
import com.example.finalproject.dto.PakStanislavRegisterRequestDto;
import com.example.finalproject.dto.PakStanislavUserDto;

public interface PakStanislavAuthService {

    PakStanislavUserDto register(PakStanislavRegisterRequestDto requestDto);

    PakStanislavAuthResponseDto login(PakStanislavLoginRequestDto requestDto);
}
