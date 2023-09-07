package com.ice.icemusic.repositories;

import com.ice.icemusic.entities.models.Alias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AliasRepository extends JpaRepository<Alias, Long> {
    @Query(value = "SELECT a.* FROM aliases a " +
            "JOIN artists ar ON ar.id = a.artist_id  WHERE a.artist_id = :artistId",
            nativeQuery = true)
    List<Alias> findAliasesByArtistId(@Param("artistId") final Long artistId);

    @Query(value = "SELECT * FROM aliases a WHERE a.artist_id = " +
            "(SELECT artist_id FROM aliases a2 JOIN artists ar ON ar.id = a2.artist_id  WHERE a2.alias = :alias)",
            nativeQuery = true)
    List<Alias> findAliasesByAliasString(@Param("alias") final String alias);

    @Query(value = "SELECT * FROM aliases a WHERE a.alias = :alias",
            nativeQuery = true)
    Optional<Alias> findAliasByAliasString(@Param("alias") final String alias);
}
