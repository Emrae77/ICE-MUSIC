package com.ice.icemusic.controllers;

import com.ice.icemusic.entities.payloads.error.ApiError;
import com.ice.icemusic.services.exceptions.ResourceConflictsException;
import com.ice.icemusic.services.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalErrorHandler {
    private static final String VALIDATION_ERROR_MESSAGE = "ValidationException: Invalid fields";

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiError> handleException(final Exception ex) {
        final ApiError error = ApiError.builder()
                .errorMessage(ex.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(value = DataAccessException.class)
    public ResponseEntity<ApiError> handleDatabaseException(final DataAccessException ex) {
        final ApiError error = ApiError.builder()
                .errorMessage(ex.getMessage())
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST)
                .build();
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(final ResourceNotFoundException ex) {
        final ApiError error = ApiError.builder()
                .errorMessage(ex.getLocalizedMessage())
                .status(HttpStatus.NOT_FOUND)
                .timeStamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(value = ResourceConflictsException.class)
    public ResponseEntity<ApiError> handleResourceConflictsException(final ResourceConflictsException ex) {
        final ApiError error = ApiError.builder()
                .errorMessage(ex.getLocalizedMessage())
                .status(HttpStatus.CONFLICT)
                .timeStamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException ex) {
        final BindingResult result = ex.getBindingResult();
        final ApiError apiError = ApiError.builder()
                .errorMessage(VALIDATION_ERROR_MESSAGE)
                .status(HttpStatus.BAD_REQUEST)
                .timeStamp(LocalDateTime.now())
                .build();

        for (FieldError fieldError: result.getFieldErrors()) {
            final com.ice.icemusic.entities.payloads.error.FieldError error =
                    new com.ice.icemusic.entities.payloads.error.FieldError();

            error.setField(fieldError.getField());
            error.setError(fieldError.getDefaultMessage());
            apiError.addFieldError(error);
        }

        return ResponseEntity.badRequest().body(apiError);
    }
}
