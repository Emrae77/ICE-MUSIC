package com.ice.icemusic.controllers;

import com.ice.icemusic.entities.payloads.Alias;
import com.ice.icemusic.entities.payloads.Artist;
import com.ice.icemusic.services.ArtistService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/artists")
public class ArtistController {
    private ArtistService artistService;

    @PostMapping
    public ResponseEntity<Artist> createArtist(@Valid @RequestBody Artist artist) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(artistService.createArtist(artist));
    }

    @PutMapping("/{artistAlias}")
    public ResponseEntity<Void> editArtistName(@PathVariable final String artistAlias, @Valid @RequestBody final Alias newAlias) {
        artistService.editArtistName(artistAlias, newAlias);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{artistAlias}")
    public ResponseEntity<Artist> getArtist(@PathVariable final String artistAlias) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(artistService.getArtist(artistAlias));
    }

    @GetMapping("/spotlight")
    public ResponseEntity<Artist> getArtistOfTheDay() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(artistService.getArtistOfTheDay());
    }
}
