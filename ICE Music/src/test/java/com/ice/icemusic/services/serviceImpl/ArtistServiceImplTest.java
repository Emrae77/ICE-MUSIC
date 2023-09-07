package com.ice.icemusic.services.serviceImpl;

import com.ice.icemusic.entities.models.Alias;
import com.ice.icemusic.entities.models.Artist;
import com.ice.icemusic.repositories.AliasRepository;
import com.ice.icemusic.repositories.ArtistRepository;
import com.ice.icemusic.services.ArtistService;
import com.ice.icemusic.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class ArtistServiceImplTest {
    private static final String ALIAS = "alias";
    private static final Long ARTIST_ID = 1L;
    @Mock
    ArtistRepository artistRepository;

    @Mock
    AliasRepository aliasRepository;

    ArtistService artistService;

    @BeforeEach
    void setup () {
        artistRepository = mock(ArtistRepository.class);
        aliasRepository = mock(AliasRepository.class);
        artistService = new ArtistServiceImpl(artistRepository, aliasRepository);
    }

    @Test
    void testArtistIsCreatedSuccessFully() {
        // Given
        final Artist artist = createArtist(ARTIST_ID);
        final Alias alias = createAlias(ALIAS, ARTIST_ID);

        final com.ice.icemusic.entities.payloads.Artist artistPayload = createArtistPayloadWithAlias(ALIAS);
        when(artistRepository.save(any())).thenReturn(artist);
        when(aliasRepository.save(alias)).thenReturn(alias);

        // When
        com.ice.icemusic.entities.payloads.Artist expectedArtist = artistService.createArtist(artistPayload);

        // Then
        assertNotNull(expectedArtist);
        assertEquals(expectedArtist.getAliases().size(), artistPayload.getAliases().size());
        assertEquals(expectedArtist.getAliases().get(0), artistPayload.getAliases().get(0));

    }

    @Test
    void editArtistName() {
        // Given
        final Alias alias = createAlias(ALIAS, ARTIST_ID);
        when(aliasRepository.findAliasByAliasString(ALIAS)).thenReturn(Optional.of(alias));

        // When
        artistService.editArtistName(ALIAS, createAliasPayload("test"));

        // Then
        verify(aliasRepository).save(any());
    }

    @Test
    void testEditArtistNameThrowsResourceNotFoundException() {
        assertThrows(ResourceNotFoundException.class, () -> artistService.editArtistName(ALIAS, createAliasPayload("test")));

        verify(aliasRepository, times(0)).save(any());
    }

    @Test
    void testArtistIsFetchedSuccessfully() {
        // Given
        final Alias alias = createAlias(ALIAS, ARTIST_ID);
        final com.ice.icemusic.entities.models.Artist artist = createArtist(ARTIST_ID);
        when(aliasRepository.findAliasesByAliasString(ALIAS)).thenReturn(List.of(alias));
        when(artistRepository.findById(ARTIST_ID)).thenReturn(Optional.of(artist));

        // When
        final com.ice.icemusic.entities.payloads.Artist expectedArtist = artistService.getArtist(ALIAS);

        // Then
        assertNotNull(expectedArtist.getAliases());
        assertEquals(expectedArtist.getAliases().size(), 1);
        assertEquals(expectedArtist.getAliases().get(0).getAlias(), ALIAS);
    }

    @Test
    void testResourceNotFoundExceptionWhenAliasIsNotFound() {
        final Alias alias = createAlias(ALIAS, ARTIST_ID);
        when(aliasRepository.findAliasesByAliasString(ALIAS)).thenReturn(List.of(alias));

        assertThrows(ResourceNotFoundException.class, () -> artistService.getArtist(ALIAS));
    }

    @Test
    void testResourceNotFoundExceptionWhenArtistIdIsNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> artistService.getArtist(ALIAS));
    }

    private Alias createAlias(final String aliasString, final Long artistId) {
        final Alias newAlias = new Alias();
        newAlias.setAlias(aliasString);
        newAlias.setArtistId(artistId);

        return newAlias;
    }

    private Artist createArtist(final Long artistId) {
        final Artist artist = new Artist();
        artist.setId(artistId);
        return artist;
    }

    private com.ice.icemusic.entities.payloads.Artist createArtistPayloadWithAlias(final String aliasString) {
        final com.ice.icemusic.entities.payloads.Artist artist = new com.ice.icemusic.entities.payloads.Artist();
        final com.ice.icemusic.entities.payloads.Alias alias = new com.ice.icemusic.entities.payloads.Alias();
        alias.setAlias(aliasString);
        artist.setAliases(List.of(alias));
        return artist;
    }

    private com.ice.icemusic.entities.payloads.Alias createAliasPayload(final String aliasString) {
        final com.ice.icemusic.entities.payloads.Alias newAlias = new com.ice.icemusic.entities.payloads.Alias();
        newAlias.setAlias(aliasString);

        return newAlias;
    }
}