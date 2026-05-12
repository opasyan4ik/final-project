package com.example.finalproject.controller;

import com.example.finalproject.dto.PakStanislavLessonDto;
import com.example.finalproject.service.PakStanislavLessonFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
public class PakStanislavLessonFileController {

    private final PakStanislavLessonFileService lessonFileService;

    @PostMapping("/{lessonId}/upload")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public ResponseEntity<PakStanislavLessonDto> uploadLessonFile(
            @PathVariable Long lessonId,
            @RequestParam("file") MultipartFile file
    ) {
        return ResponseEntity.ok(lessonFileService.uploadLessonFile(lessonId, file));
    }

    @GetMapping("/{lessonId}/download")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER','STUDENT')")
    public ResponseEntity<Resource> downloadLessonFile(@PathVariable Long lessonId) {
        Resource resource = lessonFileService.downloadLessonFile(lessonId);
        String fileName = lessonFileService.getLessonFileName(lessonId);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }
}
