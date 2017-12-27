package com.ferzerkerx.albumfinder.controller;

import com.ferzerkerx.albumfinder.model.Album;
import com.ferzerkerx.albumfinder.service.AlbumFinderService;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.Collections;
import java.util.List;

import static com.ferzerkerx.albumfinder.Util.createAlbum;
import static org.hamcrest.Matchers.greaterThan;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AlbumsController.class)
public class AlbumsControllerTest extends BaseControllerTest {

    @MockBean
    private AlbumFinderService albumFinderService;

    @Test
    public void testGetAlbums() throws Exception {
        List<Album> albums = Collections.singletonList(createAlbum());

        when(albumFinderService.findAlbumsByArtist(1)).thenReturn(albums);

        getMockMvc().perform(get("/artist/1/albums").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].title").value("some title"))
                .andExpect(jsonPath("$.data[0].year").value("2016"));

    }

    @Test
    public void testGetAlbumsEmpty() throws Exception {
        getMockMvc().perform(get("/artist/1/albums")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    public void testFindAlbumById() throws Exception {
        Album album = createAlbum();
        when(albumFinderService.findAlbumById(1)).thenReturn(album);

        getMockMvc().perform(get("/album/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.title").value("some title"))
                .andExpect(jsonPath("$.data.year").value("2016"));
    }

    @Test
    public void testSaveAlbum() throws Exception {
        Album album = createAlbum();
        album.setId(0);

        doAnswer(invocationOnMock -> {
            Album album1 = (Album) invocationOnMock.getArguments()[1];
            album1.setId(2);
            return album1;
        }).when(albumFinderService).saveAlbum(eq(1), any());

        getMockMvc().perform(post("/admin/artist/1/album")
                .with(csrf())
                .with(admin())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(album)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(greaterThan(0)));
    }

    @Test
    public void testDeleteAlbumById() throws Exception {
        doNothing().when(albumFinderService).deleteAlbumById(1);

        getMockMvc().perform(delete("/admin/album/1")
                .with(csrf())
                .with(admin())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateAlbumById() throws Exception {
        Album album = createAlbum();
        when(albumFinderService.updateAlbum(any())).thenReturn(album);

        getMockMvc().perform(put("/admin/album/1")
                .with(csrf())
                .with(admin())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(album)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(greaterThan(0)));
    }

    @Test
    public void testFindMatchedRecordByCriteria() throws Exception {
        List<Album> albums = Collections.singletonList(createAlbum());
        when(albumFinderService.findMatchedAlbumByCriteria("someTitle", "someYear")).thenReturn(albums);

        getMockMvc().perform(get("/albums/search")
                .param("title", "someTitle")
                .param("year", "someYear")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].title").value("some title"))
                .andExpect(jsonPath("$.data[0].year").value("2016"));
    }
}