package com.example.finalproject.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class PakStanislavGlobalExceptionHandler {

    @ExceptionHandler(PakStanislavResourceNotFoundException.class)
    public ResponseEntity<PakStanislavErrorResponse> handleResourceNotFound(
            PakStanislavResourceNotFoundException ex,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getRequestURI(),
                List.of()
        );
    }

    @ExceptionHandler(PakStanislavBadRequestException.class)
    public ResponseEntity<PakStanislavErrorResponse> handleBadRequest(
            PakStanislavBadRequestException ex,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getRequestURI(),
                List.of()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<PakStanislavErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        List<String> details = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> {
                    if (error instanceof FieldError fieldError) {
                        return fieldError.getField() + ": " + fieldError.getDefaultMessage();
                    }
                    return error.getDefaultMessage();
                })
                .toList();

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                request.getRequestURI(),
                details
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<PakStanislavErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request
    ) {
        List<String> details = ex.getConstraintViolations()
                .stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .toList();

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Constraint violation",
                request.getRequestURI(),
                details
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<PakStanislavErrorResponse> handleDataIntegrityViolation(
            DataIntegrityViolationException ex,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.CONFLICT,
                "Data integrity violation",
                request.getRequestURI(),
                List.of(ex.getMostSpecificCause().getMessage())
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<PakStanislavErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Unexpected server error",
                request.getRequestURI(),
                List.of(ex.getMessage())
        );
    }

    private ResponseEntity<PakStanislavErrorResponse> buildResponse(
            HttpStatus status,
            String message,
            String path,
            List<String> details
    ) {
        PakStanislavErrorResponse response = PakStanislavErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(path)
                .details(details)
                .build();

        return ResponseEntity.status(status).body(response);
    }
}
