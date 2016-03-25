package com.ferzerkerx.album_finder.controller;

import java.util.Collections;
import java.util.List;
import com.ferzerkerx.album_finder.model.Artist;
import com.ferzerkerx.album_finder.service.AlbumFinderService;
import org.junit.After;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.matchers.GreaterThan;
import org.springframework.http.MediaType;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ArtistControllerTest extends BaseControllerTest {


    private AlbumFinderService albumFinderService;

    @Override
    public void setUp() {
        super.setUp();
        albumFinderService = Mockito.mock(AlbumFinderService.class);
        setBinding(getWebApplicationContext(), AlbumFinderService.class, albumFinderService);
    }

    @After
    public void tearDown() throws Exception {
        Mockito.reset(albumFinderService);
    }

    @Test
    public void deleteArtistById() throws Exception {
        doNothing().when(albumFinderService).deleteArtistWithAlbumsById(1);

        getMockMvc().perform(delete("/admin/artist/1")//
            .with(csrf())
            .with(admin())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
        ; //
    }

    @Test
    public void findMatchedArtistsByName() throws Exception {
        List<Artist> artists = Collections.singletonList(createArtist());

        when(albumFinderService.findMatchedArtistsByName("someArtist")).thenReturn(artists);

        getMockMvc().perform(get("/artist/search")//
            .param("name", "someArtist")
            .contentType(MediaType.APPLICATION_JSON)) //
            .andExpect(status().isOk()) //
            .andExpect(jsonPath("$.data[0].id").value(1))
            .andExpect(jsonPath("$.data[0].name").value("someArtist"))
        ; //

    }

    @Test
    public void updateArtistById() throws Exception {
        Artist artist = createArtist();

        when(albumFinderService.updateArtistById(any())).thenReturn(artist);

        getMockMvc().perform(put("/admin/artist/1")//
            .with(csrf())
            .with(admin())
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(artist)))//
            .andExpect(status().isOk()) //
            .andExpect(jsonPath("$.data.id").value(1))
            .andExpect(jsonPath("$.data.name").value("someArtist"))
        ; //
    }

    @Test
    public void findArtistById() throws Exception {
        Artist artist = createArtist();

        when(albumFinderService.findArtistById(1)).thenReturn(artist);

        getMockMvc().perform(get("/artist/1")//
            .param("name", "someArtist")
            .contentType(MediaType.APPLICATION_JSON)) //
            .andExpect(status().isOk()) //
            .andExpect(jsonPath("$.data.id").value(1))
            .andExpect(jsonPath("$.data.name").value("someArtist"))
        ; //
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

        getMockMvc().perform(post("/admin/artists")//
            .with(csrf())
            .with(admin())
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(artist)))//
            .andExpect(status().isOk()) //
            .andExpect(jsonPath("$.data.id").value(new GreaterThan<>(0)))
        ; //
    }

    @Test
    public void showAllArtists() throws Exception {
        List<Artist> artists = Collections.singletonList(createArtist());

        when(albumFinderService.findAllArtists()).thenReturn(artists);

        getMockMvc().perform(get("/artists")//
            .contentType(MediaType.APPLICATION_JSON)) //
            .andExpect(status().isOk()) //
            .andExpect(jsonPath("$.data[0].id").value(1))
            .andExpect(jsonPath("$.data[0].name").value("someArtist"))
        ; //

    }
}