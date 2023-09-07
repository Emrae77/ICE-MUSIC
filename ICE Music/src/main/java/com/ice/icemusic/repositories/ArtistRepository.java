package com.ice.icemusic.repositories;

import com.ice.icemusic.entities.models.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    @Query(value = "SELECT * FROM artists a WHERE a.artist_of_the_day = TRUE", nativeQuery = true)
    Optional<Artist> findArtistByArtistOfTheDay();
}
