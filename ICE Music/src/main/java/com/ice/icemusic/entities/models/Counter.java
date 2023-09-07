package com.ice.icemusic.entities.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "counter")
public class Counter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "count")
    private Long count;
    @Column(name = "previousArtistId")
    private Long previousArtistId = 2L;
}
