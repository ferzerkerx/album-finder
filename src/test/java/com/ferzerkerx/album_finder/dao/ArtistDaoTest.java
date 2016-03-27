package com.ferzerkerx.album_finder.dao;

import java.util.List;
import com.ferzerkerx.album_finder.BaseIntegrationTest;
import com.ferzerkerx.album_finder.model.Artist;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class ArtistDaoTest extends BaseIntegrationTest {

    @Autowired
    private ArtistDao artistDao;

    @Test
    public void findMatchedArtistsByName() throws Exception {
        Artist artist = createArtist();
        artistDao.insert(artist);

        List<Artist> matchedArtistsByName = artistDao.findMatchedArtistsByName(artist.getName());
        assertNotNull(matchedArtistsByName);
        assertEquals(1, matchedArtistsByName.size());
        assertEquals(artist, matchedArtistsByName.get(0));
    }

    @Test
    public void findAllArtists() throws Exception {
        List<Artist> emptyArtists = artistDao.findAllArtists();
        assertNotNull(emptyArtists);
        assertTrue(emptyArtists.isEmpty());

        artistDao.insert(createArtist());
        artistDao.insert(createArtist());

        List<Artist> artists = artistDao.findAllArtists();
        assertNotNull(artists);
        assertEquals(2, artists.size());
    }


    @Test
    public void deleteById() throws Exception {
        Artist artist = createArtist();
        artistDao.insert(artist);

        Artist storedArtist = artistDao.findById(artist.getId());
        assertEquals(artist, storedArtist);

        artistDao.deleteById(storedArtist.getId());
        assertNull(artistDao.findById(storedArtist.getId()));
    }

    @Test
    public void delete() throws Exception {
        Artist artist = createArtist();
        artistDao.insert(artist);

        Artist storedArtist = artistDao.findById(artist.getId());
        assertEquals(artist, storedArtist);

        artistDao.delete(storedArtist);
        assertNull(artistDao.findById(storedArtist.getId()));
    }

    @Test
    public void update() throws Exception {
        Artist artist = createArtist();
        artistDao.insert(artist);

        Artist storedArtist = artistDao.findById(artist.getId());
        assertEquals(artist, storedArtist);

        storedArtist.setName("new name");
        artistDao.update(artist);

        Artist updatedArtist = artistDao.findById(storedArtist.getId());
        assertEquals(storedArtist, updatedArtist);
    }

    @Test
    public void insert() throws Exception {
        Artist artist = createArtist();
        artistDao.insert(artist);

        Artist storedArtist = artistDao.findById(artist.getId());
        assertEquals(artist, storedArtist);
    }
}