package com.ice.icemusic.entities.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class Alias {
    @NotBlank(message = "alias cannot be blank")
    private String alias;
}
