package com.ice.icemusic.controllers;

import com.ice.icemusic.entities.payloads.Track;
import com.ice.icemusic.services.TrackService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/tracks")
public class TrackController {
    private TrackService trackService;

    @PostMapping("/{artistAlias}")
    public ResponseEntity<Track> addTrack(@Valid @RequestBody final Track track, @PathVariable final String artistAlias) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(trackService.addTrack(track, artistAlias));
    }

    @GetMapping("/{artistAlias}")
    public ResponseEntity<List<Track>> fetchTracksByArtist(@PathVariable final String artistAlias) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(trackService.fetchArtistTracks(artistAlias));
    }
}
