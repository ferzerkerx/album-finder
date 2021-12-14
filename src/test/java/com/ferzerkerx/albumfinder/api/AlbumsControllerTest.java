package com.ferzerkerx.albumfinder.api;

import com.ferzerkerx.albumfinder.domain.AlbumFinderService;
import com.ferzerkerx.albumfinder.infrastructure.entity.AlbumEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static com.ferzerkerx.albumfinder.Fixtures.createAlbum;
import static com.ferzerkerx.albumfinder.api.TestUtil.admin;
import static com.ferzerkerx.albumfinder.api.TestUtil.toJson;
import static org.hamcrest.Matchers.greaterThan;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
        List<AlbumEntity> albumEntityEntities = Collections.singletonList(createAlbum());

        when(albumFinderService.findAlbumsByArtist(1)).thenReturn(albumEntityEntities);

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
        AlbumEntity albumEntity = createAlbum();
        when(albumFinderService.findAlbumById(1)).thenReturn(albumEntity);

        mockMvc.perform(get("/album/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.title").value("some title"))
                .andExpect(jsonPath("$.data.year").value("2016"));
    }

    @Test
    void testSaveAlbum() throws Exception {
        AlbumEntity albumEntity = createAlbum();
        albumEntity.setId(0);

        doAnswer(invocationOnMock -> {
            AlbumEntity albumEntity1 = (AlbumEntity) invocationOnMock.getArguments()[1];
            albumEntity1.setId(2);
            return albumEntity1;
        }).when(albumFinderService).saveAlbum(eq(1), any());

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
        AlbumEntity albumEntity = createAlbum();
        when(albumFinderService.updateAlbum(any())).thenReturn(albumEntity);

        mockMvc.perform(put("/admin/album/1")
                        .with(csrf())
                        .with(admin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(albumEntity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(greaterThan(0)));
    }

    @Test
    void testFindMatchedRecordByCriteria() throws Exception {
        List<AlbumEntity> albumEntityEntities = Collections.singletonList(createAlbum());
        when(albumFinderService.findMatchedAlbumByCriteria("someTitle", "someYear")).thenReturn(albumEntityEntities);

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