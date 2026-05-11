package com.example.finalproject.mapper;

import com.example.finalproject.dto.PakStanislavRoleDto;
import com.example.finalproject.dto.PakStanislavRoleRequestDto;
import com.example.finalproject.entity.PakStanislavRole;
import org.springframework.stereotype.Component;

@Component
public class PakStanislavRoleMapper {

    public PakStanislavRoleDto toDto(PakStanislavRole role) {
        return PakStanislavRoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }

    public PakStanislavRole toEntity(PakStanislavRoleRequestDto requestDto) {
        return PakStanislavRole.builder()
                .name(requestDto.getName())
                .build();
    }

    public void updateEntity(PakStanislavRole role, PakStanislavRoleRequestDto requestDto) {
        role.setName(requestDto.getName());
    }
}
