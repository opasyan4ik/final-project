package com.example.finalproject.dto;

import com.example.finalproject.entity.PakStanislavRoleType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PakStanislavRoleDto {

    private Long id;
    private PakStanislavRoleType name;
}
