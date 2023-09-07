package com.ice.icemusic.entities.payloads;

import jakarta.validation.Valid;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Artist {
    @Valid
    private List<Alias> aliases = new ArrayList<>();
}
