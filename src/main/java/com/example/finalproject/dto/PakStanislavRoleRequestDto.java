package com.example.finalproject.dto;

import com.example.finalproject.entity.PakStanislavRoleType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PakStanislavRoleRequestDto {

    @NotNull(message = "Role name is required")
    private PakStanislavRoleType name;
}
