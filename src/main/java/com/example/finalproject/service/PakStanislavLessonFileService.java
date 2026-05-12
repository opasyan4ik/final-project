package com.example.finalproject.service;

import com.example.finalproject.dto.PakStanislavLessonDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface PakStanislavLessonFileService {

    PakStanislavLessonDto uploadLessonFile(Long lessonId, MultipartFile file);

    Resource downloadLessonFile(Long lessonId);

    String getLessonFileName(Long lessonId);
}
