package com.ice.icemusic.entities.payloads.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldError {
    private String field;
    private String error;
}
