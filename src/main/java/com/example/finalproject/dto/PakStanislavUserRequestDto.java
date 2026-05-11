package com.example.finalproject.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class PakStanislavUserRequestDto {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean enabled;
    private Set<Long> roleIds;
}
