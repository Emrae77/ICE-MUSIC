package com.ice.icemusic.entities.payloads;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Track {
    @NotBlank(message = "track title cannot be blank")
    private String trackTitle;
    @NotBlank(message = "genre cannot be blank")
    private String genre;
    private int length;
}
