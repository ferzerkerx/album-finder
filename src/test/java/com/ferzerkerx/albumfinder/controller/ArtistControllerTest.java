package com.ferzerkerx.albumfinder.controller;

import com.ferzerkerx.albumfinder.model.Artist;
import com.ferzerkerx.albumfinder.service.AlbumFinderService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.Collections;
import java.util.List;

import static com.ferzerkerx.albumfinder.Util.createArtist;
import static org.hamcrest.Matchers.greaterThan;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ArtistController.class)
public class ArtistControllerTest extends BaseControllerTest {

    @MockBean
    private AlbumFinderService albumFinderService;

    @Test
    public void deleteArtistById() throws Exception {
        doNothing().when(albumFinderService).deleteArtistWithAlbumsById(1);

        getMockMvc().perform(delete("/admin/artist/1")
            .with(csrf())
            .with(admin())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
        ;
    }

    @Test
    public void findMatchedArtistsByName() throws Exception {
        List<Artist> artists = Collections.singletonList(createArtist());

        when(albumFinderService.findMatchedArtistsByName("someArtist")).thenReturn(artists);

        getMockMvc().perform(get("/artist/search")
            .param("name", "someArtist")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data[0].id").value(1))
            .andExpect(jsonPath("$.data[0].name").value("someArtist"))
        ;

    }

    @Test
    public void updateArtistById() throws Exception {
        Artist artist = createArtist();

        when(albumFinderService.updateArtist(any())).thenReturn(artist);

        getMockMvc().perform(put("/admin/artist/1")
            .with(csrf())
            .with(admin())
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(artist)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.id").value(1))
            .andExpect(jsonPath("$.data.name").value("someArtist"))
        ;
    }

    @Test
    public void findArtistById() throws Exception {
        Artist artist = createArtist();

        when(albumFinderService.findArtistById(1)).thenReturn(artist);

        getMockMvc().perform(get("/artist/1")
            .param("name", "someArtist")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.id").value(1))
            .andExpect(jsonPath("$.data.name").value("someArtist"))
        ;
    }

    @Test
    public void saveArtist() throws Exception {
        Artist artist = createArtist();
        artist.setId(0);

        doAnswer(invocationOnMock -> {
            Artist artist1 = (Artist) invocationOnMock.getArguments()[0];
            artist1.setId(2);
            return artist1;
        }).when(albumFinderService).saveArtist(any());

        getMockMvc().perform(post("/admin/artist")
            .with(csrf())
            .with(admin())
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(artist)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.id").value(greaterThan(0)))
        ;
    }
}