package com.ferzerkerx.albumfinder.infrastructure;

import com.ferzerkerx.albumfinder.Fixtures;
import com.ferzerkerx.albumfinder.domain.Album;
import com.ferzerkerx.albumfinder.domain.AlbumFinderService;
import com.ferzerkerx.albumfinder.domain.Artist;
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
        albumFinderService.deleteArtistById(artistId);
        verify(albumRepository).deleteRecordsByArtistId(artistId);
        verify(artistRepository).deleteById(artistId);
    }

    @Test
    void updateAlbum() {
        Album album = new Album(1, "someTitle", "1995", new Artist(1, "artistName"));
        final AlbumEntity albumEntity = Fixtures.albumEntity();
        albumEntity.setArtist(Fixtures.artistEntity());
        when(albumRepository.update(any())).thenReturn(albumEntity);
        albumFinderService.updateAlbum(album);
        verify(albumRepository).update(any());
    }

    @Test
    void updateArtist() {
        Artist artist = new Artist(1, "artistName");
        when(artistRepository.update(any())).thenReturn(Fixtures.artistEntity());
        albumFinderService.updateArtist(artist);
        verify(artistRepository).update(any());
    }

    @Test
    void saveAlbum() {
        Integer artistId = 1;
        Album album = new Album(1, "someTitle", "1995", new Artist(artistId, "artistName"));

        ArgumentCaptor<AlbumEntity> albumArgumentCaptor = ArgumentCaptor.forClass(AlbumEntity.class);

        albumFinderService.saveAlbum(artistId, album);
        verify(albumRepository).insert(albumArgumentCaptor.capture());

        AlbumEntity capturedAlbumEntity = albumArgumentCaptor.getValue();
        assertEquals(album.title(), capturedAlbumEntity.getTitle());

        ArtistEntity capturedAlbumArtistEntity = capturedAlbumEntity.getArtist();
        assertNotNull(capturedAlbumArtistEntity);
        assertEquals(artistId, capturedAlbumArtistEntity.getId());
    }

    @Test
    void saveArtist() {
        Artist artist = new Artist(1, "artistName");
        albumFinderService.saveArtist(artist);
        verify(artistRepository).insert(any());
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
        final AlbumEntity albumEntity = Fixtures.albumEntity();
        albumEntity.setArtist(Fixtures.artistEntity());

        when(albumRepository.findById(any())).thenReturn(albumEntity);
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
        albumFinderService.findArtistsByName(name);
        verify(artistRepository).findArtistsByName(name);
    }

    @Test
    void findArtistById() {
        int artistId = 1;
        when(artistRepository.findById(any())).thenReturn(Fixtures.artistEntity());
        albumFinderService.findArtistById(artistId);
        verify(artistRepository).findById(artistId);
    }

    @Test
    void findAllArtists() {
        albumFinderService.findAllArtists();
        verify(artistRepository).findAllArtists();
    }

}