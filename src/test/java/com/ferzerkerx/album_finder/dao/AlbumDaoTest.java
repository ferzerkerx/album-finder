package com.ferzerkerx.album_finder.dao;

import java.util.List;
import com.ferzerkerx.album_finder.BaseIntegrationTest;
import com.ferzerkerx.album_finder.model.Album;
import com.ferzerkerx.album_finder.model.Artist;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class AlbumDaoTest extends BaseIntegrationTest {

    @Autowired
    private AlbumDao albumDao;

    @Autowired
    private ArtistDao artistDao;

    private Artist artist;

    @Before
    public void setUp() throws Exception {
        artist = createArtist();
        artistDao.insert(artist);
    }

    @Test
    public void deleteRecordsByArtistId() throws Exception {
        Album album = createAlbum(artist);
        albumDao.insert(album);

        List<Album> recordsByArtist = albumDao.findRecordsByArtist(artist.getId());
        assertNotNull(recordsByArtist);
        assertEquals(1, recordsByArtist.size());

        albumDao.deleteRecordsByArtistId(artist.getId());

        List<Album> emptyByArtist = albumDao.findRecordsByArtist(artist.getId());
        assertNotNull(emptyByArtist);
        assertTrue(emptyByArtist.isEmpty());
    }

    @Test
    public void deleteById() throws Exception {
        Album album = createAlbum(artist);

        albumDao.insert(album);
        int id = album.getId();
        assertNotNull(albumDao.findById(id));

        albumDao.deleteById(id);
        assertNull(albumDao.findById(id));
    }

    @Test
    public void delete() throws Exception {
        Album album = createAlbum(artist);

        albumDao.insert(album);
        int id = album.getId();
        assertNotNull(albumDao.findById(id));

        albumDao.delete(album);
        assertNull(albumDao.findById(id));
    }

    @Test
    public void update() throws Exception {
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
    public void insert() throws Exception {
        Album album = createAlbum(artist);
        albumDao.insert(album);

        assertEquals(album, albumDao.findById(album.getId()));
    }

    @Test
    public void findByCriteria() throws Exception {
        Album album = createAlbum(artist);
        albumDao.insert(album);

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
    public void findRecordsByArtist() throws Exception {
        List<Album> emptyRecordsByArtist = albumDao.findRecordsByArtist(artist.getId());
        assertNotNull(emptyRecordsByArtist);
        assertTrue(emptyRecordsByArtist.isEmpty());

        Album album = createAlbum(artist);
        albumDao.insert(album);

        List<Album> recordsByArtist = albumDao.findRecordsByArtist(artist.getId());
        assertNotNull(recordsByArtist);
        assertEquals(1, recordsByArtist.size());

        assertEquals(album, recordsByArtist.get(0));
    }
}