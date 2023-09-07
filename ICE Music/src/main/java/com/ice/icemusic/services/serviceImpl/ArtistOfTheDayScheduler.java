package com.ice.icemusic.services.serviceImpl;

import com.ice.icemusic.entities.models.Artist;
import com.ice.icemusic.entities.models.Counter;
import com.ice.icemusic.repositories.ArtistRepository;
import com.ice.icemusic.repositories.CounterRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ArtistOfTheDayScheduler {

    private ArtistRepository artistRepository;
    private CounterRepository counterRepository;

    @Scheduled(cron = "0 0 * * * ?")
    public void selectArtistOfTheDay() {
        long artistsSize = artistRepository.count();
        final Counter counter = counterRepository.findCount();

        if (counter == null || artistsSize == 0) {
            return;
        }

        setArtistOfTheDayForNextArtistId(artistsSize, counter);
        counterRepository.save(counter);
    }

    private void setArtistOfTheDayForNextArtistId(final long artistsSize, final Counter counter) {
        for (int i = 0;  i < artistsSize; i++) {
            Optional<Artist> artistOfTheDayOptional = artistRepository.findById(counter.getCount());
            if (artistOfTheDayOptional.isPresent()) {
                final Artist artistOfTheDay = artistOfTheDayOptional.get();
                setAndSaveArtistOfTheDay(artistOfTheDay, true);

                Optional<Artist> previousArtistOfTheDayOptional = artistRepository.findById(counter.getPreviousArtistId());
                previousArtistOfTheDayOptional.ifPresent(value -> setAndSaveArtistOfTheDay(value, false));

                updateCounter(counter, artistsSize, artistOfTheDay);
                return;
            }
        }
    }

    private void setAndSaveArtistOfTheDay (Artist artistOfTheDay, final boolean isArtistOfTheDay) {
        artistOfTheDay.setArtistOfTheDay(isArtistOfTheDay);
        artistRepository.save(artistOfTheDay);
    }

    private void updateCounter(final Counter counter, final long artistsSize, final Artist artistOfTheDay) {
        counter.setPreviousArtistId(artistOfTheDay.getId());
        incrementCount(counter, artistsSize);
    }

    private void incrementCount(final Counter counter, final long artistsSize) {
        if (counter.getCount() >= artistsSize) {
            counter.setCount(1L);
        } else {
            counter.setCount(counter.getCount() + 1L);
        }
    }
}
