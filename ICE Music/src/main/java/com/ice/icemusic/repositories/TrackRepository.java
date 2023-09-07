package com.ice.icemusic.repositories;

import com.ice.icemusic.entities.models.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {
    @Query(value = "SELECT * FROM tracks t WHERE t.artist_id = :artistId",
            nativeQuery = true)
    List<Track> findTrackByArtistId(@Param("artistId") final Long artistId);
}
