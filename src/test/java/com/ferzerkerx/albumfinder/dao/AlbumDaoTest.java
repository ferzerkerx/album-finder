package com.ferzerkerx.albumfinder.dao;

import com.ferzerkerx.albumfinder.BaseIntegrationTest;
import com.ferzerkerx.albumfinder.model.Album;
import com.ferzerkerx.albumfinder.model.Artist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.ferzerkerx.albumfinder.Util.createAlbum;
import static com.ferzerkerx.albumfinder.Util.createArtist;
import static org.junit.jupiter.api.Assertions.*;

class AlbumDaoTest extends BaseIntegrationTest {

    @Autowired
    private AlbumDao albumDao;

    @Autowired
    private ArtistDao artistDao;

    private Artist artist;

    @BeforeEach
    void setUp() {
        artist = createArtist();
        artistDao.insert(artist);
    }

    @Test
    void deleteRecordsByArtistId() {
        Album album = createAlbum(artist);
        albumDao.insert(album);

        flush();

        List<Album> recordsByArtist = albumDao.findRecordsByArtist(artist.getId());
        assertNotNull(recordsByArtist);
        assertEquals(1, recordsByArtist.size());

        albumDao.deleteRecordsByArtistId(artist.getId());

        List<Album> emptyByArtist = albumDao.findRecordsByArtist(artist.getId());
        assertNotNull(emptyByArtist);
        assertTrue(emptyByArtist.isEmpty());
    }

    @Test
    void deleteById() {
        Album album = createAlbum(artist);

        albumDao.insert(album);
        int id = album.getId();
        assertNotNull(albumDao.findById(id));

        albumDao.deleteById(id);
        assertNull(albumDao.findById(id));
    }

    @Test
    void delete() {
        Album album = createAlbum(artist);

        albumDao.insert(album);
        int id = album.getId();
        assertNotNull(albumDao.findById(id));

        albumDao.delete(album);
        assertNull(albumDao.findById(id));
    }

    @Test
    void update() {
        Album album = createAlbum(artist);
        albumDao.insert(album);

        Album fetchedAlbum = albumDao.findById(album.getId());
        assertEquals(album, fetchedAlbum);

        fetchedAlbum.setTitle("some title");
        fetchedAlbum.setYear("2004");

        albumDao.update(fetchedAlbum);

        Album updatedAlbum = albumDao.findById(album.getId());
        assertEquals(fetchedAlbum, updatedAlbum);

    }

    @Test
    void insert() {
        Album album = createAlbum(artist);
        albumDao.insert(album);

        assertEquals(album, albumDao.findById(album.getId()));
    }

    @Test
    void findByCriteria() {
        Album album = createAlbum(artist);
        albumDao.insert(album);

        flush();

        List<Album> byFullCriteria = albumDao.findByCriteria(album);
        assertNotNull(byFullCriteria);
        assertEquals(1, byFullCriteria.size());
        assertEquals(album, byFullCriteria.get(0));

        Album titleAlbum = new Album();
        titleAlbum.setTitle(album.getTitle());

        List<Album> byTitle = albumDao.findByCriteria(titleAlbum);
        assertNotNull(byTitle);
        assertEquals(1, byTitle.size());
        assertEquals(album, byTitle.get(0));


        Album yearAlbum = new Album();
        yearAlbum.setYear(album.getYear());

        List<Album> byYear = albumDao.findByCriteria(yearAlbum);
        assertNotNull(byYear);
        assertEquals(1, byYear.size());
        assertEquals(album, byYear.get(0));
    }

    @Test
    void findRecordsByArtist() {
        List<Album> emptyRecordsByArtist = albumDao.findRecordsByArtist(artist.getId());
        assertNotNull(emptyRecordsByArtist);
        assertTrue(emptyRecordsByArtist.isEmpty());

        Album album = createAlbum(artist);
        albumDao.insert(album);

        flush();

        List<Album> recordsByArtist = albumDao.findRecordsByArtist(artist.getId());
        assertNotNull(recordsByArtist);
        assertEquals(1, recordsByArtist.size());

        assertEquals(album, recordsByArtist.get(0));
    }
}