package com.example.finalproject.mapper;

import com.example.finalproject.dto.PakStanislavRoleDto;
import com.example.finalproject.dto.PakStanislavUserDto;
import com.example.finalproject.dto.PakStanislavUserRequestDto;
import com.example.finalproject.entity.PakStanislavRole;
import com.example.finalproject.entity.PakStanislavUser;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PakStanislavUserMapper {

    public PakStanislavUserDto toDto(PakStanislavUser user) {
        Set<PakStanislavRoleDto> roleDtos = user.getRoles() == null
                ? Collections.emptySet()
                : user.getRoles().stream()
                .map(role -> PakStanislavRoleDto.builder()
                        .id(role.getId())
                        .name(role.getName())
                        .build())
                .collect(Collectors.toSet());

        return PakStanislavUserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .enabled(user.getEnabled())
                .roles(roleDtos)
                .build();
    }

    public PakStanislavUser toEntity(PakStanislavUserRequestDto requestDto, Set<PakStanislavRole> roles) {
        return PakStanislavUser.builder()
                .firstName(requestDto.getFirstName())
                .lastName(requestDto.getLastName())
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .enabled(requestDto.getEnabled() != null ? requestDto.getEnabled() : Boolean.TRUE)
                .roles(roles)
                .build();
    }

    public void updateEntity(PakStanislavUser user, PakStanislavUserRequestDto requestDto, Set<PakStanislavRole> roles) {
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setEmail(requestDto.getEmail());
        user.setPassword(requestDto.getPassword());
        user.setEnabled(requestDto.getEnabled() != null ? requestDto.getEnabled() : user.getEnabled());
        user.setRoles(roles);
    }
}
