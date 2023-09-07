package com.ice.icemusic.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceConflictsException extends RuntimeException{
    public ResourceConflictsException(final String message) {
        super(message);
    }
}
