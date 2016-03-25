package com.ferzerkerx.album_finder.dao;

import java.util.List;
import com.ferzerkerx.album_finder.BaseIntegrationTest;
import com.ferzerkerx.album_finder.model.Album;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class AlbumDaoTest extends BaseIntegrationTest {

    @Autowired
    private AlbumDao albumDao;

    @Test
    public void deleteRecordsByArtistId() throws Exception {

        fail();
    }

    @Test
    public void deleteById() throws Exception {
        fail();
    }

    @Test
    public void delete() throws Exception {
        fail();
    }

    @Test
    public void update() throws Exception {
        fail();
    }

    @Test
    public void insert() throws Exception {
        Album album = createAlbum();
        albumDao.insert(album);

        assertTrue(album.getId() > 1);
    }

    @Test
    public void findByCriteria() throws Exception {
        Album album = createAlbum();
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
        fail();
    }
}