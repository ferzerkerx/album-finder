package com.ferzerkerx.albumfinder.infrastructure;

import com.ferzerkerx.albumfinder.domain.AlbumFinderService;
import com.ferzerkerx.albumfinder.infrastructure.entity.AlbumEntity;
import com.ferzerkerx.albumfinder.infrastructure.entity.ArtistEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class AlbumFinderServiceImplTest {

    private AlbumRepository albumRepository;
    private AlbumFinderService albumFinderService;
    private ArtistRepository artistRepository;


    @BeforeEach
    void setUp() {
        artistRepository = mock(ArtistRepository.class);
        albumRepository = mock(AlbumRepository.class);
        albumFinderService = new AlbumFinderServiceImpl(artistRepository, albumRepository);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(albumRepository);
        verifyNoMoreInteractions(artistRepository);
    }

    @Test
    void deleteAlbumById() {
        int albumId = 1;
        albumFinderService.deleteAlbumById(albumId);
        verify(albumRepository).deleteById(albumId);
    }

    @Test
    void deleteArtistWithAlbumsById() {
        int artistId = 1;
        albumFinderService.deleteArtistWithAlbumsById(artistId);
        verify(albumRepository).deleteRecordsByArtistId(artistId);
        verify(artistRepository).deleteById(artistId);
    }

    @Test
    void updateAlbum() {
        AlbumEntity albumEntity = new AlbumEntity();
        albumFinderService.updateAlbum(albumEntity);
        verify(albumRepository).update(albumEntity);
    }

    @Test
    void updateArtist() {
        ArtistEntity artistEntity = new ArtistEntity();
        albumFinderService.updateArtist(artistEntity);
        verify(artistRepository).update(artistEntity);
    }

    @Test
    void saveAlbum() {
        AlbumEntity albumEntity = new AlbumEntity();
        albumEntity.setTitle("someTitle");
        albumEntity.setYear("1995");
        Integer artistId = 1;

        ArgumentCaptor<AlbumEntity> albumArgumentCaptor = ArgumentCaptor.forClass(AlbumEntity.class);

        albumFinderService.saveAlbum(artistId, albumEntity);
        verify(albumRepository).insert(albumArgumentCaptor.capture());

        AlbumEntity capturedAlbumEntity = albumArgumentCaptor.getValue();
        assertEquals(albumEntity, capturedAlbumEntity);

        ArtistEntity capturedAlbumArtistEntity = capturedAlbumEntity.getArtist();
        assertNotNull(capturedAlbumArtistEntity);
        assertEquals(artistId, capturedAlbumArtistEntity.getId());
    }

    @Test
    void saveArtist() {
        ArtistEntity artistEntity = new ArtistEntity();
        albumFinderService.saveArtist(artistEntity);
        verify(artistRepository).insert(artistEntity);
    }

    @Test
    void findAlbumsByArtist() {
        int artistId = 1;
        albumFinderService.findAlbumsByArtist(artistId);
        verify(albumRepository).findRecordsByArtist(artistId);
    }

    @Test
    void findAlbumById() {
        int albumId = 1;
        albumFinderService.findAlbumById(albumId);
        verify(albumRepository).findById(albumId);
    }

    @Test
    void findMatchedAlbumByCriteria() {
        ArgumentCaptor<AlbumEntity> albumArgumentCaptor = ArgumentCaptor.forClass(AlbumEntity.class);

        String title = "someTitle";
        String year = "1995";
        albumFinderService.findMatchedAlbumByCriteria(title, year);
        verify(albumRepository).findByCriteria(albumArgumentCaptor.capture());

        AlbumEntity capturedAlbumEntity = albumArgumentCaptor.getValue();
        assertNotNull(capturedAlbumEntity);
        assertEquals(title, capturedAlbumEntity.getTitle());
        assertEquals(year, capturedAlbumEntity.getYear());
    }

    @Test
    void findMatchedArtistsByName() {
        String name = "name";
        albumFinderService.findMatchedArtistsByName(name);
        verify(artistRepository).findArtistsByName(name);
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