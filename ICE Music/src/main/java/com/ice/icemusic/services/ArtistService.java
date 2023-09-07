package com.ice.icemusic.services;

import com.ice.icemusic.entities.payloads.Alias;
import com.ice.icemusic.entities.payloads.Artist;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArtistService {
    Artist createArtist(Artist artist);

    Artist getArtistOfTheDay();

    void editArtistName(String artistAlias, Alias newAlias);

    Artist getArtist(String artistAlias);

    com.ice.icemusic.entities.models.Artist getArtistModel(String artistAlias);
}
