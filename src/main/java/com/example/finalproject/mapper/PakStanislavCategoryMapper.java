package com.example.finalproject.mapper;

import com.example.finalproject.dto.PakStanislavCategoryDto;
import com.example.finalproject.dto.PakStanislavCategoryRequestDto;
import com.example.finalproject.entity.PakStanislavCategory;
import org.springframework.stereotype.Component;

@Component
public class PakStanislavCategoryMapper {

    public PakStanislavCategoryDto toDto(PakStanislavCategory category) {
        return PakStanislavCategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .courseCount(category.getCourses() != null ? category.getCourses().size() : 0)
                .build();
    }

    public PakStanislavCategory toEntity(PakStanislavCategoryRequestDto requestDto) {
        return PakStanislavCategory.builder()
                .name(requestDto.getName())
                .description(requestDto.getDescription())
                .build();
    }

    public void updateEntity(PakStanislavCategory category, PakStanislavCategoryRequestDto requestDto) {
        category.setName(requestDto.getName());
        category.setDescription(requestDto.getDescription());
    }
}
