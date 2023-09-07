package com.ice.icemusic.entities.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity(name = "aliases")
@Table(name = "aliases", uniqueConstraints =
{@UniqueConstraint(columnNames={"alias", "artistId"})})
public class Alias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "alias", unique = true)
    private String alias;
    @Column(name = "artistId")
    private Long artistId;
}
