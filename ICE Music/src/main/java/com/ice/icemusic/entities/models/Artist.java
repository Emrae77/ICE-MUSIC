package com.ice.icemusic.entities.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "artists")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "artistOfTheDay")
    private boolean artistOfTheDay = false;
}
