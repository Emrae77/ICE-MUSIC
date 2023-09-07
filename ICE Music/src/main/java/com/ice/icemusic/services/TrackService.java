package com.ice.icemusic.services;

import com.ice.icemusic.entities.payloads.Track;

import java.util.List;


public interface TrackService {
    Track addTrack(Track track, String artistAlias);

    List<Track> fetchArtistTracks (String artistName);
}
