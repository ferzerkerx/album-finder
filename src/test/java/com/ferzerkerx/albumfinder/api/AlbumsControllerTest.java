package com.ferzerkerx.albumfinder.api;

import com.ferzerkerx.albumfinder.domain.AlbumFinderService;
import com.ferzerkerx.albumfinder.infrastructure.entity.AlbumEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.ferzerkerx.albumfinder.Fixtures.album;
import static com.ferzerkerx.albumfinder.Fixtures.albumEntity;
import static com.ferzerkerx.albumfinder.api.TestUtil.admin;
import static com.ferzerkerx.albumfinder.api.TestUtil.toJson;
import static org.hamcrest.Matchers.greaterThan;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AlbumsController.class)
class AlbumsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlbumFinderService albumFinderService;

    @Test
    void testGetAlbums() throws Exception {

        when(albumFinderService.findAlbumsByArtist(1)).thenReturn(List.of(album()));

        mockMvc.perform(get("/artist/1/albums").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].title").value("some title"))
                .andExpect(jsonPath("$.data[0].year").value("2016"));

    }

    @Test
    void testGetAlbumsEmpty() throws Exception {
        mockMvc.perform(get("/artist/1/albums")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testFindAlbumById() throws Exception {
        when(albumFinderService.findAlbumById(1)).thenReturn(album());

        mockMvc.perform(get("/album/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.title").value("some title"))
                .andExpect(jsonPath("$.data.year").value("2016"));
    }

    @Test
    void testSaveAlbum() throws Exception {
        AlbumEntity albumEntity = albumEntity();
        albumEntity.setId(0);

        doAnswer(invocationOnMock -> {
            AlbumEntity albumEntity1 = (AlbumEntity) invocationOnMock.getArguments()[1];
            albumEntity1.setId(2);
            return albumEntity1;
        }).when(albumFinderService).saveAlbum(any());

        mockMvc.perform(post("/admin/artist/1/album")
                        .with(csrf())
                        .with(admin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(albumEntity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(greaterThan(0)));
    }

    @Test
    void testDeleteAlbumById() throws Exception {
        doNothing().when(albumFinderService).deleteAlbumById(1);

        mockMvc.perform(delete("/admin/album/1")
                        .with(csrf())
                        .with(admin())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateAlbumById() throws Exception {
        when(albumFinderService.updateAlbum(any())).thenReturn(album());

        mockMvc.perform(put("/admin/album/1")
                        .with(csrf())
                        .with(admin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(albumEntity())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(greaterThan(0)));
    }

    @Test
    void testFindMatchedRecordByCriteria() throws Exception {
        when(albumFinderService.findMatchedAlbumByCriteria("someTitle", "someYear")).thenReturn(List.of(album()));

        mockMvc.perform(get("/albums/search")
                        .param("title", "someTitle")
                        .param("year", "someYear")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].title").value("some title"))
                .andExpect(jsonPath("$.data[0].year").value("2016"));
    }
}