package com.example.finalproject.service.impl;

import com.example.finalproject.dto.PakStanislavCourseDto;
import com.example.finalproject.dto.PakStanislavCourseRequestDto;
import com.example.finalproject.entity.PakStanislavCategory;
import com.example.finalproject.entity.PakStanislavCourse;
import com.example.finalproject.entity.PakStanislavCourseLevel;
import com.example.finalproject.exception.PakStanislavBadRequestException;
import com.example.finalproject.exception.PakStanislavResourceNotFoundException;
import com.example.finalproject.mapper.PakStanislavCourseMapper;
import com.example.finalproject.repository.PakStanislavCategoryRepository;
import com.example.finalproject.repository.PakStanislavCourseRepository;
import com.example.finalproject.service.PakStanislavCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class PakStanislavCourseServiceImpl implements PakStanislavCourseService {

    private final PakStanislavCourseRepository courseRepository;
    private final PakStanislavCategoryRepository categoryRepository;
    private final PakStanislavCourseMapper courseMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<PakStanislavCourseDto> getCourses(
            int page,
            int size,
            String sortBy,
            String sortDir,
            String keyword,
            PakStanislavCourseLevel level,
            Boolean published,
            Long categoryId,
            BigDecimal minPrice,
            BigDecimal maxPrice
    ) {
        if (page < 0) {
            throw new PakStanislavBadRequestException("Page index must be >= 0");
        }
        if (size <= 0) {
            throw new PakStanislavBadRequestException("Page size must be > 0");
        }
        if (size > 100) {
            throw new PakStanislavBadRequestException("Page size must be <= 100");
        }
        if (minPrice != null && maxPrice != null && minPrice.compareTo(maxPrice) > 0) {
            throw new PakStanislavBadRequestException("minPrice must be less than or equal to maxPrice");
        }

        Sort sort = buildSort(sortBy, sortDir);
        Pageable pageable = PageRequest.of(page, size, sort);
        Specification<PakStanislavCourse> specification = buildSpecification(
                keyword,
                level,
                published,
                categoryId,
                minPrice,
                maxPrice
        );

        return courseRepository.findAll(specification, pageable)
                .map(courseMapper::toDto);
    }

    private Specification<PakStanislavCourse> buildSpecification(
            String keyword,
            PakStanislavCourseLevel level,
            Boolean published,
            Long categoryId,
            BigDecimal minPrice,
            BigDecimal maxPrice
    ) {
        Specification<PakStanislavCourse> specification = Specification.where(null);

        if (keyword != null && !keyword.isBlank()) {
            String normalizedKeyword = "%" + keyword.toLowerCase(Locale.ROOT) + "%";
            specification = specification.and((root, query, cb) ->
                    cb.or(
                            cb.like(cb.lower(root.get("title")), normalizedKeyword),
                            cb.like(cb.lower(root.get("description")), normalizedKeyword)
                    )
            );
        }

        if (level != null) {
            specification = specification.and((root, query, cb) -> cb.equal(root.get("level"), level));
        }

        if (published != null) {
            specification = specification.and((root, query, cb) -> cb.equal(root.get("published"), published));
        }

        if (categoryId != null) {
            specification = specification.and((root, query, cb) -> cb.equal(root.get("category").get("id"), categoryId));
        }

        if (minPrice != null) {
            specification = specification.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), minPrice));
        }

        if (maxPrice != null) {
            specification = specification.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), maxPrice));
        }

        return specification;
    }

    private Sort buildSort(String sortBy, String sortDir) {
        List<String> allowedSortFields = List.of("id", "title", "price", "durationInHours", "level", "published", "createdAt");
        String normalizedSortBy = (sortBy == null || sortBy.isBlank()) ? "createdAt" : sortBy;

        if (!allowedSortFields.contains(normalizedSortBy)) {
            throw new PakStanislavBadRequestException("Unsupported sortBy field: " + normalizedSortBy);
        }

        String normalizedSortDir = (sortDir == null || sortDir.isBlank()) ? "desc" : sortDir.toLowerCase(Locale.ROOT);
        Sort.Direction direction;
        if ("asc".equals(normalizedSortDir)) {
            direction = Sort.Direction.ASC;
        } else if ("desc".equals(normalizedSortDir)) {
            direction = Sort.Direction.DESC;
        } else {
            throw new PakStanislavBadRequestException("sortDir must be either 'asc' or 'desc'");
        }

        return Sort.by(direction, normalizedSortBy);
    }

    @Override
    @Transactional(readOnly = true)
    public PakStanislavCourseDto getCourseById(Long id) {
        PakStanislavCourse course = findCourseById(id);
        return courseMapper.toDto(course);
    }

    @Override
    @Transactional
    public PakStanislavCourseDto createCourse(PakStanislavCourseRequestDto requestDto) {
        PakStanislavCategory category = findCategoryById(requestDto.getCategoryId());
        PakStanislavCourse course = courseMapper.toEntity(requestDto, category);
        PakStanislavCourse savedCourse = courseRepository.save(course);
        return courseMapper.toDto(savedCourse);
    }

    @Override
    @Transactional
    public PakStanislavCourseDto updateCourse(Long id, PakStanislavCourseRequestDto requestDto) {
        PakStanislavCourse course = findCourseById(id);
        PakStanislavCategory category = findCategoryById(requestDto.getCategoryId());
        courseMapper.updateEntity(course, requestDto, category);
        PakStanislavCourse updatedCourse = courseRepository.save(course);
        return courseMapper.toDto(updatedCourse);
    }

    @Override
    @Transactional
    public void deleteCourse(Long id) {
        PakStanislavCourse course = findCourseById(id);
        courseRepository.delete(course);
    }

    private PakStanislavCourse findCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new PakStanislavResourceNotFoundException("Course not found with id: " + id));
    }

    private PakStanislavCategory findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new PakStanislavResourceNotFoundException("Category not found with id: " + id));
    }
}
