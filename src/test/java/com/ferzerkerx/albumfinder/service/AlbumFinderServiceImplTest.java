package com.ferzerkerx.albumfinder.service;

import com.ferzerkerx.albumfinder.model.Album;
import com.ferzerkerx.albumfinder.model.Artist;
import com.ferzerkerx.albumfinder.repository.AlbumRepository;
import com.ferzerkerx.albumfinder.repository.ArtistRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class AlbumFinderServiceImplTest {

    private AlbumRepository albumDao;
    private AlbumFinderService albumFinderService;
    private ArtistRepository artistRepository;


    @BeforeEach
    void setUp() {
        artistRepository = mock(ArtistRepository.class);
        albumDao = mock(AlbumRepository.class);
        albumFinderService = new AlbumFinderServiceImpl(artistRepository, albumDao);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(albumDao);
        verifyNoMoreInteractions(artistRepository);
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
        verify(artistRepository).deleteById(artistId);
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
        verify(artistRepository).update(artist);
    }

    @Test
    void saveAlbum() {
        Album album = new Album();
        album.setTitle("someTitle");
        album.setYear("1995");
        Integer artistId = 1;

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
        verify(artistRepository).insert(artist);
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
        verify(artistRepository).findMatchedArtistsByName(name);
    }

    @Test
    void findArtistById() {
        int artistId = 1;
        albumFinderService.findArtistById(artistId);
        verify(artistRepository).findById(artistId);
    }

    @Test
    void findAllArtists() {
        albumFinderService.findAllArtists();
        verify(artistRepository).findAllArtists();
    }

}