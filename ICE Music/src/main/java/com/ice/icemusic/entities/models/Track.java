package com.ice.icemusic.entities.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "tracks")
@Table(uniqueConstraints =
{@UniqueConstraint(columnNames={"artistId", "trackTitle"})})
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "trackTitle")
    private String trackTitle;
    @Column(name = "genre")
    private String genre;
    @Column(name = "length")
    private int length;
    @Column(name = "artistId")
    private Long artistId;
}
