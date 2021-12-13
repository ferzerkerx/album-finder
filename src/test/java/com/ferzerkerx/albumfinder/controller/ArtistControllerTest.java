package com.ferzerkerx.albumfinder.controller;

import com.ferzerkerx.albumfinder.TestConfig;
import com.ferzerkerx.albumfinder.model.Artist;
import com.ferzerkerx.albumfinder.service.AlbumFinderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static com.ferzerkerx.albumfinder.Util.createArtist;
import static com.ferzerkerx.albumfinder.controller.TestUtil.admin;
import static com.ferzerkerx.albumfinder.controller.TestUtil.toJson;
import static org.hamcrest.Matchers.greaterThan;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ArtistController.class, TestConfig.class})
@WebMvcTest
class ArtistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlbumFinderService albumFinderService;

    @Test
    void deleteArtistById() throws Exception {
        doNothing().when(albumFinderService).deleteArtistWithAlbumsById(1);

        mockMvc.perform(delete("/admin/artist/1")
                        .with(csrf())
                        .with(admin())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void findMatchedArtistsByName() throws Exception {
        List<Artist> artists = Collections.singletonList(createArtist());

        when(albumFinderService.findMatchedArtistsByName("someArtist")).thenReturn(artists);

        mockMvc.perform(get("/artist/search")
                        .param("name", "someArtist")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("someArtist"));

    }

    @Test
    void updateArtistById() throws Exception {
        Artist artist = createArtist();

        when(albumFinderService.updateArtist(any())).thenReturn(artist);

        mockMvc.perform(put("/admin/artist/1")
                        .with(csrf())
                        .with(admin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(artist)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("someArtist"));
    }

    @Test
    void findArtistById() throws Exception {
        Artist artist = createArtist();

        when(albumFinderService.findArtistById(1)).thenReturn(artist);

        mockMvc.perform(get("/artist/1")
                        .param("name", "someArtist")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("someArtist"));
    }

    @Test
    void saveArtist() throws Exception {
        Artist artist = createArtist();
        artist.setId(0);

        doAnswer(invocationOnMock -> {
            Artist artist1 = (Artist) invocationOnMock.getArguments()[0];
            artist1.setId(2);
            return artist1;
        }).when(albumFinderService).saveArtist(any());

        mockMvc.perform(post("/admin/artist")
                        .with(csrf())
                        .with(admin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(artist)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(greaterThan(0)));
    }
}