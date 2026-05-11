package com.example.finalproject.service;

import com.example.finalproject.dto.PakStanislavCategoryDto;
import com.example.finalproject.dto.PakStanislavCategoryRequestDto;

import java.util.List;

public interface PakStanislavCategoryService {

    List<PakStanislavCategoryDto> getAllCategories();

    PakStanislavCategoryDto getCategoryById(Long id);

    PakStanislavCategoryDto createCategory(PakStanislavCategoryRequestDto requestDto);

    PakStanislavCategoryDto updateCategory(Long id, PakStanislavCategoryRequestDto requestDto);

    void deleteCategory(Long id);
}
