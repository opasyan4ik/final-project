package com.example.finalproject.mapper;

import com.example.finalproject.dto.PakStanislavEnrollmentDto;
import com.example.finalproject.dto.PakStanislavEnrollmentRequestDto;
import com.example.finalproject.entity.PakStanislavCourse;
import com.example.finalproject.entity.PakStanislavEnrollment;
import com.example.finalproject.entity.PakStanislavUser;
import org.springframework.stereotype.Component;

@Component
public class PakStanislavEnrollmentMapper {

    public PakStanislavEnrollmentDto toDto(PakStanislavEnrollment enrollment) {
        return PakStanislavEnrollmentDto.builder()
                .id(enrollment.getId())
                .status(enrollment.getStatus())
                .enrolledAt(enrollment.getEnrolledAt())
                .userId(enrollment.getUser().getId())
                .userFullName(enrollment.getUser().getFirstName() + " " + enrollment.getUser().getLastName())
                .courseId(enrollment.getCourse().getId())
                .courseTitle(enrollment.getCourse().getTitle())
                .build();
    }

    public PakStanislavEnrollment toEntity(PakStanislavEnrollmentRequestDto requestDto,
                                           PakStanislavUser user,
                                           PakStanislavCourse course) {
        return PakStanislavEnrollment.builder()
                .status(requestDto.getStatus())
                .user(user)
                .course(course)
                .build();
    }

    public void updateEntity(PakStanislavEnrollment enrollment,
                             PakStanislavEnrollmentRequestDto requestDto,
                             PakStanislavUser user,
                             PakStanislavCourse course) {
        enrollment.setStatus(requestDto.getStatus());
        enrollment.setUser(user);
        enrollment.setCourse(course);
    }
}
