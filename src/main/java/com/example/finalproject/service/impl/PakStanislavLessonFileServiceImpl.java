package com.example.finalproject.service.impl;

import com.example.finalproject.dto.PakStanislavLessonDto;
import com.example.finalproject.entity.PakStanislavLesson;
import com.example.finalproject.exception.PakStanislavBadRequestException;
import com.example.finalproject.exception.PakStanislavResourceNotFoundException;
import com.example.finalproject.mapper.PakStanislavLessonMapper;
import com.example.finalproject.repository.PakStanislavLessonRepository;
import com.example.finalproject.service.PakStanislavLessonFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PakStanislavLessonFileServiceImpl implements PakStanislavLessonFileService {

    private final PakStanislavLessonRepository lessonRepository;
    private final PakStanislavLessonMapper lessonMapper;

    @Value("${pakstanislav.app.file-upload-dir}")
    private String fileUploadDir;

    @Override
    @Transactional
    public PakStanislavLessonDto uploadLessonFile(Long lessonId, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new PakStanislavBadRequestException("File must not be empty");
        }

        PakStanislavLesson lesson = findLessonById(lessonId);
        Path uploadPath = getUploadPath();

        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename() == null ? "" : file.getOriginalFilename());
        String extension = "";
        int extensionIndex = originalFilename.lastIndexOf('.');
        if (extensionIndex >= 0) {
            extension = originalFilename.substring(extensionIndex);
        }

        String storedFileName = UUID.randomUUID() + extension;
        Path targetPath = uploadPath.resolve(storedFileName).normalize();

        try {
            Files.createDirectories(uploadPath);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new PakStanislavBadRequestException("Failed to upload lesson file");
        }

        lesson.setAttachmentPath(storedFileName);
        PakStanislavLesson updatedLesson = lessonRepository.save(lesson);
        return lessonMapper.toDto(updatedLesson);
    }

    @Override
    @Transactional(readOnly = true)
    public Resource downloadLessonFile(Long lessonId) {
        PakStanislavLesson lesson = findLessonById(lessonId);
        if (lesson.getAttachmentPath() == null || lesson.getAttachmentPath().isBlank()) {
            throw new PakStanislavResourceNotFoundException("No attachment found for lesson id: " + lessonId);
        }

        Path filePath = getUploadPath().resolve(lesson.getAttachmentPath()).normalize();
        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                throw new PakStanislavResourceNotFoundException("Lesson file not found");
            }
            return resource;
        } catch (MalformedURLException ex) {
            throw new PakStanislavBadRequestException("Invalid file path");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public String getLessonFileName(Long lessonId) {
        PakStanislavLesson lesson = findLessonById(lessonId);
        if (lesson.getAttachmentPath() == null || lesson.getAttachmentPath().isBlank()) {
            throw new PakStanislavResourceNotFoundException("No attachment found for lesson id: " + lessonId);
        }
        return lesson.getAttachmentPath();
    }

    private PakStanislavLesson findLessonById(Long lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new PakStanislavResourceNotFoundException("Lesson not found with id: " + lessonId));
    }

    private Path getUploadPath() {
        return Paths.get(fileUploadDir).toAbsolutePath().normalize();
    }
}
