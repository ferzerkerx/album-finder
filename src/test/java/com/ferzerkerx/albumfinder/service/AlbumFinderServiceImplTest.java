package com.ferzerkerx.albumfinder.service;

import com.ferzerkerx.albumfinder.dao.AlbumDao;
import com.ferzerkerx.albumfinder.dao.ArtistDao;
import com.ferzerkerx.albumfinder.model.Album;
import com.ferzerkerx.albumfinder.model.Artist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

//TODO fix
//@ExtendWith(AlbumFinderServiceImplTest.NoMoreInteractionsExtension.class)
public class AlbumFinderServiceImplTest {

    private AlbumDao albumDao;
    private AlbumFinderService albumFinderService;
    private ArtistDao artistDao;


    @BeforeEach
    public void setUp() {
        artistDao = mock(ArtistDao.class);
        albumDao = mock(AlbumDao.class);
        albumFinderService = new AlbumFinderServiceImpl(artistDao, albumDao);
    }

    @Test
    public void deleteAlbumById() {
        int albumId = 1;
        albumFinderService.deleteAlbumById(albumId);
        verify(albumDao).deleteById(albumId);
    }

    @Test
    public void deleteArtistWithAlbumsById() {
        int artistId = 1;
        albumFinderService.deleteArtistWithAlbumsById(artistId);
        verify(albumDao).deleteRecordsByArtistId(artistId);
        verify(artistDao).deleteById(artistId);
    }

    @Test
    public void updateAlbum() {
        Album album = new Album();
        albumFinderService.updateAlbum(album);
        verify(albumDao).update(album);
    }

    @Test
    public void updateArtist() {
        Artist artist = new Artist();
        albumFinderService.updateArtist(artist);
        verify(artistDao).update(artist);
    }

    @Test
    public void saveAlbum() {
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
    public void saveArtist() {
        Artist artist = new Artist();
        albumFinderService.saveArtist(artist);
        verify(artistDao).insert(artist);
    }

    @Test
    public void findAlbumsByArtist() {
        int artistId = 1;
        albumFinderService.findAlbumsByArtist(artistId);
        verify(albumDao).findRecordsByArtist(artistId);
    }

    @Test
    public void findAlbumById() {
        int albumId = 1;
        albumFinderService.findAlbumById(albumId);
        verify(albumDao).findById(albumId);
    }

    @Test
    public void findMatchedAlbumByCriteria() {
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
    public void findMatchedArtistsByName() {
        String name = "name";
        albumFinderService.findMatchedArtistsByName(name);
        verify(artistDao).findMatchedArtistsByName(name);
    }

    @Test
    public void findArtistById() {
        int artistId = 1;
        albumFinderService.findArtistById(artistId);
        verify(artistDao).findById(artistId);
    }

    @Test
    public void findAllArtists() {
        albumFinderService.findAllArtists();
        verify(artistDao).findAllArtists();
    }

    public final class NoMoreInteractionsExtension implements AfterEachCallback {

        @Override
        public void afterEach(ExtensionContext extensionContext) throws Exception {
            verifyNoMoreInteractions(albumDao);
            verifyNoMoreInteractions(artistDao);
        }
    }
}