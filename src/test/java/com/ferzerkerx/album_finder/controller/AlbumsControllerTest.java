package com.ferzerkerx.album_finder.controller;

import java.util.Collections;
import java.util.List;
import com.ferzerkerx.album_finder.AbstractControllerIntegrationTest;
import com.ferzerkerx.album_finder.model.Album;
import com.ferzerkerx.album_finder.model.Artist;
import com.ferzerkerx.album_finder.service.AlbumFinderService;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AlbumsControllerTest extends AbstractControllerIntegrationTest {

    private AlbumFinderService albumFinderService;

    @Override
    public void setUp() {
        super.setUp();
        albumFinderService = Mockito.mock(AlbumFinderService.class);
        setBinding(getWebApplicationContext(), AlbumFinderService.class, albumFinderService);
    }

    @Test
    public void testGetAlbums() throws Exception {

        List<Album> albums = Collections.singletonList(createAlbum());

        when(albumFinderService.findAlbumsByArtist(1)).thenReturn(albums);

        getMockMvc().perform(get("/artist/1/albums")//
            .contentType(MediaType.APPLICATION_JSON)) //
            .andExpect(status().isOk()) //
            .andExpect(jsonPath("$.data[0].id").value(1))
            .andExpect(jsonPath("$.data[0].title").value("some title"))
            .andExpect(jsonPath("$.data[0].year").value("2016"))
        ; //


    }

    @Test
    public void testGetAlbumsEmpty() throws Exception {
        getMockMvc().perform(get("/artist/1/albums")//
            .contentType(MediaType.APPLICATION_JSON)) //
            .andExpect(status().isOk()) //
            .andExpect(jsonPath("$.data").isEmpty()); //
    }

    @Test
    public void testFindAlbumById() throws Exception {
        Album album = createAlbum();
        when(albumFinderService.findAlbumById(1)).thenReturn(album);

        getMockMvc().perform(get("/album/1")//
            .contentType(MediaType.APPLICATION_JSON)) //
            .andExpect(status().isOk()) //
            .andExpect(jsonPath("$.data.id").value(1))
            .andExpect(jsonPath("$.data.title").value("some title"))
            .andExpect(jsonPath("$.data.year").value("2016"))
        ; //
    }

    @Test
    public void testSaveAlbum() throws Exception {
        fail();
    }

    @Test
    public void testDeleteAlbumById() throws Exception {
        fail();
    }

    @Test
    public void testUpdateAlbumById() throws Exception {
        fail();
    }

    @Test
    public void testFindMatchedRecordByCriteriaWithNoSpecifiedCriteria() throws Exception {
        getMockMvc().perform(get("/albums/search")//
            .contentType(MediaType.APPLICATION_JSON)) //
            .andExpect(status().isOk()) //
            .andExpect(jsonPath("$.meta.errorMessage").value("At least one search criteria must be specified"))
        ; //
    }

    @Test
    public void testFindMatchedRecordByCriteria() throws Exception {

        List<Album> albums = Collections.singletonList(createAlbum());
        when(albumFinderService.findMatchedRecordByCriteria("someTitle", "someYear")).thenReturn(albums);

        getMockMvc().perform(get("/albums/search?title=someTitle&year=someYear")//
            .contentType(MediaType.APPLICATION_JSON)) //
            .andExpect(status().isOk()) //
            .andExpect(jsonPath("$.data[0].id").value(1))
            .andExpect(jsonPath("$.data[0].title").value("some title"))
            .andExpect(jsonPath("$.data[0].year").value("2016"))
        ; //
    }

    private Album createAlbum() {
        Artist artist = new Artist();
        artist.setName("some name");
        artist.setId(10);

        Album album = new Album();
        album.setTitle("some title");
        album.setArtist(artist);
        album.setYear("2016");
        album.setId(1);
        return album;
    }
}