package com.ice.icemusic.services.serviceImpl;

import com.ice.icemusic.entities.payloads.Track;
import com.ice.icemusic.repositories.TrackRepository;
import com.ice.icemusic.services.ArtistService;
import com.ice.icemusic.services.TrackService;
import com.ice.icemusic.services.exceptions.ResourceConflictsException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.ice.icemusic.services.mappers.Mapper.map;

@Service
@AllArgsConstructor
public class TrackServiceImpl implements TrackService {
    final TrackRepository trackRepository;
    final ArtistService artistService;

    @Override
    public Track addTrack(final Track track, final String artistAlias) {
        final com.ice.icemusic.entities.models.Artist artist = artistService.getArtistModel(artistAlias);
        final com.ice.icemusic.entities.models.Track mappedTrack = map(track, com.ice.icemusic.entities.models.Track.class);
        mappedTrack.setArtistId(artist.getId());
        try {
            trackRepository.save(mappedTrack);
        } catch (final DataAccessException ex) {
            throw new ResourceConflictsException("track with title " +
                    mappedTrack.getTrackTitle() + " and artist " +
                    artistAlias + "already exists");
        }
        return track;
    }

    @Override
    public List<Track> fetchArtistTracks(final String artistAlias) {
        final com.ice.icemusic.entities.models.Artist artist = artistService.getArtistModel(artistAlias);
        List<com.ice.icemusic.entities.models.Track> trackListByArtist = trackRepository.findTrackByArtistId(artist.getId());
        return trackListByArtist.stream().map(track -> map(track, Track.class)).collect(Collectors.toList());
    }
}
