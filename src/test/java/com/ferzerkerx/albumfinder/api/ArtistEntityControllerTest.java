package com.ferzerkerx.albumfinder.api;

import com.ferzerkerx.albumfinder.domain.AlbumFinderService;
import com.ferzerkerx.albumfinder.infrastructure.entity.ArtistEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static com.ferzerkerx.albumfinder.Fixtures.createArtist;
import static com.ferzerkerx.albumfinder.api.TestUtil.admin;
import static com.ferzerkerx.albumfinder.api.TestUtil.toJson;
import static org.hamcrest.Matchers.greaterThan;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ArtistController.class)
class ArtistEntityControllerTest {

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
        List<ArtistEntity> artistEntities = Collections.singletonList(createArtist());

        when(albumFinderService.findMatchedArtistsByName("someArtist")).thenReturn(artistEntities);

        mockMvc.perform(get("/artist/search")
                        .param("name", "someArtist")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("someArtist"));

    }

    @Test
    void updateArtistById() throws Exception {
        ArtistEntity artistEntity = createArtist();

        when(albumFinderService.updateArtist(any())).thenReturn(artistEntity);

        mockMvc.perform(put("/admin/artist/1")
                        .with(csrf())
                        .with(admin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(artistEntity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("someArtist"));
    }

    @Test
    void findArtistById() throws Exception {
        ArtistEntity artistEntity = createArtist();

        when(albumFinderService.findArtistById(1)).thenReturn(artistEntity);

        mockMvc.perform(get("/artist/1")
                        .param("name", "someArtist")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("someArtist"));
    }

    @Test
    void saveArtist() throws Exception {
        ArtistEntity artistEntity = createArtist();
        artistEntity.setId(0);

        doAnswer(invocationOnMock -> {
            ArtistEntity artistEntity1 = (ArtistEntity) invocationOnMock.getArguments()[0];
            artistEntity1.setId(2);
            return artistEntity1;
        }).when(albumFinderService).saveArtist(any());

        mockMvc.perform(post("/admin/artist")
                        .with(csrf())
                        .with(admin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(artistEntity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(greaterThan(0)));
    }
}