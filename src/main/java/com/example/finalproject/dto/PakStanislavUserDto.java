package com.example.finalproject.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class PakStanislavUserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean enabled;
    private Set<PakStanislavRoleDto> roles;
}
