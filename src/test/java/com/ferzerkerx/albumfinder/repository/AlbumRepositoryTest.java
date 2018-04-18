package com.ferzerkerx.albumfinder.repository;

import com.ferzerkerx.albumfinder.DbIntegrationTest;
import com.ferzerkerx.albumfinder.model.Album;
import com.ferzerkerx.albumfinder.model.Artist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.ferzerkerx.albumfinder.Util.createAlbum;
import static com.ferzerkerx.albumfinder.Util.createArtist;
import static org.junit.jupiter.api.Assertions.*;

class AlbumRepositoryTest extends DbIntegrationTest {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistRepository artistRepository;

    private Artist artist;

    @BeforeEach
    void setUp() {
        artist = createArtist();
        artistRepository.insert(artist);
    }

    @Test
    void deleteRecordsByArtistId() {
        Album album = createAlbum(artist);
        albumRepository.insert(album);

        flush();

        List<Album> recordsByArtist = albumRepository.findRecordsByArtist(artist.getId());
        assertNotNull(recordsByArtist);
        assertEquals(1, recordsByArtist.size());

        albumRepository.deleteRecordsByArtistId(artist.getId());

        List<Album> emptyByArtist = albumRepository.findRecordsByArtist(artist.getId());
        assertNotNull(emptyByArtist);
        assertTrue(emptyByArtist.isEmpty());
    }

    @Test
    void deleteById() {
        Album album = createAlbum(artist);

        albumRepository.insert(album);
        int id = album.getId();
        assertNotNull(albumRepository.findById(id));

        albumRepository.deleteById(id);
        assertNull(albumRepository.findById(id));
    }

    @Test
    void delete() {
        Album album = createAlbum(artist);

        albumRepository.insert(album);
        Integer id = album.getId();
        assertNotNull(albumRepository.findById(id));

        albumRepository.delete(album);
        assertNull(albumRepository.findById(id));
    }

    @Test
    void update() {
        Album album = createAlbum(artist);
        albumRepository.insert(album);

        Album fetchedAlbum = albumRepository.findById(album.getId());
        assertEquals(album, fetchedAlbum);

        fetchedAlbum.setTitle("some title");
        fetchedAlbum.setYear("2004");

        albumRepository.update(fetchedAlbum);

        Album updatedAlbum = albumRepository.findById(album.getId());
        assertEquals(fetchedAlbum, updatedAlbum);

    }

    @Test
    void insert() {
        Album album = createAlbum(artist);
        albumRepository.insert(album);

        assertEquals(album, albumRepository.findById(album.getId()));
    }

    @Test
    void findByCriteria() {
        Album album = createAlbum(artist);
        albumRepository.insert(album);

        flush();

        List<Album> byFullCriteria = albumRepository.findByCriteria(album);
        assertNotNull(byFullCriteria);
        assertEquals(1, byFullCriteria.size());
        assertEquals(album, byFullCriteria.get(0));

        Album titleAlbum = new Album();
        titleAlbum.setTitle(album.getTitle());

        List<Album> byTitle = albumRepository.findByCriteria(titleAlbum);
        assertNotNull(byTitle);
        assertEquals(1, byTitle.size());
        assertEquals(album, byTitle.get(0));
        assertNotNull(byTitle.get(0).getArtist());

        Album yearAlbum = new Album();
        yearAlbum.setYear(album.getYear());

        List<Album> byYear = albumRepository.findByCriteria(yearAlbum);
        assertNotNull(byYear);
        assertEquals(1, byYear.size());
        assertEquals(album, byYear.get(0));
        assertNotNull(byTitle.get(0).getArtist());
    }

    @Test
    void findRecordsByArtist() {
        List<Album> emptyRecordsByArtist = albumRepository.findRecordsByArtist(artist.getId());
        assertNotNull(emptyRecordsByArtist);
        assertTrue(emptyRecordsByArtist.isEmpty());

        Album album = createAlbum(artist);
        albumRepository.insert(album);

        flush();

        List<Album> recordsByArtist = albumRepository.findRecordsByArtist(artist.getId());
        assertNotNull(recordsByArtist);
        assertEquals(1, recordsByArtist.size());

        assertEquals(album, recordsByArtist.get(0));
    }
}