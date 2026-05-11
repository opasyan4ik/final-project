package com.example.finalproject.service.impl;

import com.example.finalproject.dto.PakStanislavCategoryDto;
import com.example.finalproject.dto.PakStanislavCategoryRequestDto;
import com.example.finalproject.entity.PakStanislavCategory;
import com.example.finalproject.exception.PakStanislavBadRequestException;
import com.example.finalproject.exception.PakStanislavResourceNotFoundException;
import com.example.finalproject.mapper.PakStanislavCategoryMapper;
import com.example.finalproject.repository.PakStanislavCategoryRepository;
import com.example.finalproject.service.PakStanislavCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PakStanislavCategoryServiceImpl implements PakStanislavCategoryService {

    private final PakStanislavCategoryRepository categoryRepository;
    private final PakStanislavCategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public List<PakStanislavCategoryDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PakStanislavCategoryDto getCategoryById(Long id) {
        PakStanislavCategory category = findCategoryById(id);
        return categoryMapper.toDto(category);
    }

    @Override
    @Transactional
    public PakStanislavCategoryDto createCategory(PakStanislavCategoryRequestDto requestDto) {
        if (categoryRepository.existsByName(requestDto.getName())) {
            throw new PakStanislavBadRequestException("Category with this name already exists");
        }

        PakStanislavCategory category = categoryMapper.toEntity(requestDto);
        PakStanislavCategory savedCategory = categoryRepository.save(category);
        return categoryMapper.toDto(savedCategory);
    }

    @Override
    @Transactional
    public PakStanislavCategoryDto updateCategory(Long id, PakStanislavCategoryRequestDto requestDto) {
        PakStanislavCategory category = findCategoryById(id);

        if (!category.getName().equals(requestDto.getName()) && categoryRepository.existsByName(requestDto.getName())) {
            throw new PakStanislavBadRequestException("Category with this name already exists");
        }

        categoryMapper.updateEntity(category, requestDto);
        PakStanislavCategory updatedCategory = categoryRepository.save(category);
        return categoryMapper.toDto(updatedCategory);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        PakStanislavCategory category = findCategoryById(id);
        categoryRepository.delete(category);
    }

    private PakStanislavCategory findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new PakStanislavResourceNotFoundException("Category not found with id: " + id));
    }
}
