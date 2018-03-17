package com.ferzerkerx.albumfinder.service;

import com.ferzerkerx.albumfinder.dao.AlbumDao;
import com.ferzerkerx.albumfinder.dao.ArtistDao;
import com.ferzerkerx.albumfinder.model.Album;
import com.ferzerkerx.albumfinder.model.Artist;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

class AlbumFinderServiceImplTest {

    private AlbumDao albumDao;
    private AlbumFinderService albumFinderService;
    private ArtistDao artistDao;


    @BeforeEach
    void setUp() {
        artistDao = mock(ArtistDao.class);
        albumDao = mock(AlbumDao.class);
        albumFinderService = new AlbumFinderServiceImpl(artistDao, albumDao);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(albumDao);
        verifyNoMoreInteractions(artistDao);
    }

    @Test
    void deleteAlbumById() {
        int albumId = 1;
        albumFinderService.deleteAlbumById(albumId);
        verify(albumDao).deleteById(albumId);
    }

    @Test
    void deleteArtistWithAlbumsById() {
        int artistId = 1;
        albumFinderService.deleteArtistWithAlbumsById(artistId);
        verify(albumDao).deleteRecordsByArtistId(artistId);
        verify(artistDao).deleteById(artistId);
    }

    @Test
    void updateAlbum() {
        Album album = new Album();
        albumFinderService.updateAlbum(album);
        verify(albumDao).update(album);
    }

    @Test
    void updateArtist() {
        Artist artist = new Artist();
        albumFinderService.updateArtist(artist);
        verify(artistDao).update(artist);
    }

    @Test
    void saveAlbum() {
        Album album = new Album();
        album.setTitle("someTitle");
        album.setYear("1995");
        int artistId = 1;

        ArgumentCaptor<Album> albumArgumentCaptor = ArgumentCaptor.forClass(Album.class);

        albumFinderService.saveAlbum(artistId, album);
        verify(albumDao).insert(albumArgumentCaptor.capture());

        Album capturedAlbum = albumArgumentCaptor.getValue();
        assertEquals(album, capturedAlbum);

        Artist capturedAlbumArtist = capturedAlbum.getArtist();
        assertNotNull(capturedAlbumArtist);
        assertEquals(artistId, capturedAlbumArtist.getId());
    }

    @Test
    void saveArtist() {
        Artist artist = new Artist();
        albumFinderService.saveArtist(artist);
        verify(artistDao).insert(artist);
    }

    @Test
    void findAlbumsByArtist() {
        int artistId = 1;
        albumFinderService.findAlbumsByArtist(artistId);
        verify(albumDao).findRecordsByArtist(artistId);
    }

    @Test
    void findAlbumById() {
        int albumId = 1;
        albumFinderService.findAlbumById(albumId);
        verify(albumDao).findById(albumId);
    }

    @Test
    void findMatchedAlbumByCriteria() {
        ArgumentCaptor<Album> albumArgumentCaptor = ArgumentCaptor.forClass(Album.class);

        String title = "someTitle";
        String year = "1995";
        albumFinderService.findMatchedAlbumByCriteria(title, year);
        verify(albumDao).findByCriteria(albumArgumentCaptor.capture());

        Album capturedAlbum = albumArgumentCaptor.getValue();
        assertNotNull(capturedAlbum);
        assertEquals(title, capturedAlbum.getTitle());
        assertEquals(year, capturedAlbum.getYear());
    }

    @Test
    void findMatchedArtistsByName() {
        String name = "name";
        albumFinderService.findMatchedArtistsByName(name);
        verify(artistDao).findMatchedArtistsByName(name);
    }

    @Test
    void findArtistById() {
        int artistId = 1;
        albumFinderService.findArtistById(artistId);
        verify(artistDao).findById(artistId);
    }

    @Test
    void findAllArtists() {
        albumFinderService.findAllArtists();
        verify(artistDao).findAllArtists();
    }

}