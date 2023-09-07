package com.ice.icemusic.services.serviceImpl;

import com.ice.icemusic.entities.payloads.Alias;
import com.ice.icemusic.entities.payloads.Artist;
import com.ice.icemusic.repositories.AliasRepository;
import com.ice.icemusic.repositories.ArtistRepository;
import com.ice.icemusic.services.ArtistService;
import com.ice.icemusic.services.exceptions.ResourceConflictsException;
import com.ice.icemusic.services.exceptions.ResourceNotFoundException;

import lombok.AllArgsConstructor;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ice.icemusic.services.mappers.Mapper.map;

@Service
@AllArgsConstructor
public class ArtistServiceImpl implements ArtistService {
    private final ArtistRepository artistRepository;
    private final AliasRepository aliasRepository;
    @Override
    @Transactional
    public Artist createArtist(final Artist artist) {
        final com.ice.icemusic.entities.models.Artist savedArtist =
                artistRepository.save(new com.ice.icemusic.entities.models.Artist());
        final Long savedIdOfArtist = savedArtist.getId();
        mapAndSaveAliasModel(artist, savedIdOfArtist);
        return artist;
    }

    @Override
    public Artist getArtistOfTheDay() {
        com.ice.icemusic.entities.models.Artist artist = artistRepository.findArtistByArtistOfTheDay().orElseThrow(() ->
                new ResourceNotFoundException("No artist of the day!"));
        return mapArtist(artist, getAliasList(artist.getId()));
    }

    @Override
    public void editArtistName(final String artistAlias, final Alias newAlias) {
        final Optional<com.ice.icemusic.entities.models.Alias> optionalAlias = aliasRepository.findAliasByAliasString(artistAlias);
        final com.ice.icemusic.entities.models.Alias alias =
                optionalAlias.orElseThrow(() -> new ResourceNotFoundException("artist with alias " + artistAlias + " not found"));
        final com.ice.icemusic.entities.models.Alias mappedAlias = map(newAlias, com.ice.icemusic.entities.models.Alias.class);
        mappedAlias.setArtistId(alias.getArtistId());

        aliasRepository.save(mappedAlias);
    }

    @Override
    public Artist getArtist(final String artistAlias) {
        final List<com.ice.icemusic.entities.models.Alias> artistAliases = getAliasList(artistAlias);

        return mapArtist(findArtistByAlias(popFirstAliasFromAliasList(artistAliases)), artistAliases);
    }

    @Override
    public com.ice.icemusic.entities.models.Artist getArtistModel(final String artistAlias) {
        final List<com.ice.icemusic.entities.models.Alias> artistAliases = getAliasList(artistAlias);
        return findArtistByAlias(popFirstAliasFromAliasList(artistAliases));
    }

    private List<com.ice.icemusic.entities.models.Alias> getAliasList(final String artistAlias) {
        final List<com.ice.icemusic.entities.models.Alias> artistAliases = aliasRepository.findAliasesByAliasString(artistAlias);
        if (artistAliases.isEmpty()) {
           throw new ResourceNotFoundException("alias " + artistAlias + " not found");
        }
        return artistAliases;
    }

    private List<com.ice.icemusic.entities.models.Alias> getAliasList(final Long artistId) {
        final List<com.ice.icemusic.entities.models.Alias> artistAliases = aliasRepository.findAliasesByArtistId(artistId);
        if (artistAliases.isEmpty()) {
            throw new ResourceNotFoundException("alias with id " + artistId + " not found");
        }
        return artistAliases;
    }

    private com.ice.icemusic.entities.models.Artist findArtistByAlias(final com.ice.icemusic.entities.models.Alias alias) {
        if (alias != null) {
            final Optional<com.ice.icemusic.entities.models.Artist> optionalArtist = artistRepository.findById(alias.getArtistId());
            if (optionalArtist.isPresent()) {
                return optionalArtist.get();
            }
        }
        throw new ResourceNotFoundException("artist with id " + alias.getArtistId() + " not found");
    }

    private void mapAndSaveAliasModel (final Artist artist, final Long artistId) {
        artist.getAliases().forEach(aliasPayload -> {
            final com.ice.icemusic.entities.models.Alias alias = new com.ice.icemusic.entities.models.Alias();
            alias.setAlias(aliasPayload.getAlias());
            alias.setArtistId(artistId);
            try {
                aliasRepository.save(alias);
            } catch (final DataAccessException e) {
                throw new ResourceConflictsException("artist with alias " + alias.getAlias() + " already exists");
            }
        });
    }

    private Artist mapArtist(final com.ice.icemusic.entities.models.Artist artist, final List<com.ice.icemusic.entities.models.Alias> aliases) {
        final Artist artistPayload = map(artist, Artist.class);
        artistPayload.setAliases(aliases.stream().map(alias -> map(alias, Alias.class)).collect(Collectors.toList()));

        return artistPayload;
    }

    private com.ice.icemusic.entities.models.Alias popFirstAliasFromAliasList(final List<com.ice.icemusic.entities.models.Alias> aliases) {
        if (aliases.isEmpty()) {
            return null;
        }
        return aliases.get(0);
    }
}
