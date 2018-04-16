package com.ferzerkerx.albumfinder.repository;

import com.ferzerkerx.albumfinder.DbIntegrationTest;
import com.ferzerkerx.albumfinder.model.Artist;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.ferzerkerx.albumfinder.Util.createArtist;
import static org.junit.jupiter.api.Assertions.*;

@Disabled
class ArtistRepositoryTest extends DbIntegrationTest {

    @Autowired
    private ArtistRepository artistRepository;

    @Test
    void findMatchedArtistsByName() {
        Artist artist = createArtist();
        artistRepository.insert(artist);

        flush();

        List<Artist> matchedArtistsByName = artistRepository.findMatchedArtistsByName(artist.getName());
        assertNotNull(matchedArtistsByName);
        assertEquals(1, matchedArtistsByName.size());
        assertEquals(artist, matchedArtistsByName.get(0));
    }

    @Test
    void findAllArtists() {
        List<Artist> emptyArtists = artistRepository.findAllArtists();
        assertNotNull(emptyArtists);
        assertTrue(emptyArtists.isEmpty());

        artistRepository.insert(createArtist());
        artistRepository.insert(createArtist());

        flush();

        List<Artist> artists = artistRepository.findAllArtists();
        assertNotNull(artists);
        assertEquals(2, artists.size());
    }


    @Test
    void deleteById() {
        Artist artist = createArtist();
        artistRepository.insert(artist);

        Artist storedArtist = artistRepository.findById(artist.getId());
        assertEquals(artist, storedArtist);

        artistRepository.deleteById(storedArtist.getId());
        assertNull(artistRepository.findById(storedArtist.getId()));
    }

    @Test
    void delete() {
        Artist artist = createArtist();
        artistRepository.insert(artist);

        Artist storedArtist = artistRepository.findById(artist.getId());
        assertEquals(artist, storedArtist);

        artistRepository.delete(storedArtist);
        assertNull(artistRepository.findById(storedArtist.getId()));
    }

    @Test
    void update() {
        Artist artist = createArtist();
        artistRepository.insert(artist);

        Artist storedArtist = artistRepository.findById(artist.getId());
        assertEquals(artist, storedArtist);

        storedArtist.setName("new name");
        artistRepository.update(artist);

        Artist updatedArtist = artistRepository.findById(storedArtist.getId());
        assertEquals(storedArtist, updatedArtist);
    }

    @Test
    void insert() {
        Artist artist = createArtist();
        artistRepository.insert(artist);

        Artist storedArtist = artistRepository.findById(artist.getId());
        assertEquals(artist, storedArtist);
    }
}