package com.ice.icemusic.entities.payloads.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ApiError {
    @JsonFormat(pattern = "yyyy-MM-dd:HH:mm:ss")
    private LocalDateTime timeStamp;

    private String errorMessage;

    private List<FieldError> fieldErrors;

    private HttpStatus status;

    public void addFieldError(final FieldError fieldError) {
        if (fieldErrors == null ) {
            fieldErrors = new ArrayList<>();
        }

        fieldErrors.add(fieldError);
    }
}
