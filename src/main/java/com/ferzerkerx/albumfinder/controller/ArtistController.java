package com.ferzerkerx.albumfinder.controller;

import com.ferzerkerx.albumfinder.model.Artist;
import com.ferzerkerx.albumfinder.model.Response;
import com.ferzerkerx.albumfinder.service.AlbumFinderService;
import com.ferzerkerx.albumfinder.service.ResponseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArtistController extends AbstractController {

    private final AlbumFinderService albumFinderService;

    @Autowired
    protected ArtistController(ResponseEntityService responseEntityService, AlbumFinderService albumFinderService) {
        super(responseEntityService);
        this.albumFinderService = albumFinderService;
    }

    @PostMapping(value = {"/admin/artist"})
    public ResponseEntity<Response<Artist>> saveArtist(@RequestBody ArtistDto artistDto) {
        Artist artist = artistDto.toEntity();
        albumFinderService.saveArtist(artist);
        return data(artist);
    }

    @GetMapping(value = {"/artist/{id}"})
    public ResponseEntity<Response<Artist>> findArtistById(@PathVariable(value = "id") int artistId) {
        return data(albumFinderService.findArtistById(artistId));
    }

    @DeleteMapping(value = {"/admin/artist/{id}"})
    public void deleteArtistById(@PathVariable(value = "id") int artistId) {
        albumFinderService.deleteArtistWithAlbumsById(artistId);
    }

    @PutMapping(value = {"/admin/artist/{id}"})
    public ResponseEntity<Response<Artist>> updateArtistById(@PathVariable(value = "id") int artistId, @RequestBody ArtistDto artistDto) {
        artistDto.setId(artistId);
        Artist updateArtist = albumFinderService.updateArtist(artistDto.toEntity());
        return data(updateArtist);
    }

    @GetMapping(value = {"/artist/search"})
    public ResponseEntity<Response<List<Artist>>> findMatchedArtistsByName(@RequestParam(value = "name") String name) {
        List<Artist> matchedArtistsByName = albumFinderService.findMatchedArtistsByName(name);
        return data(matchedArtistsByName);
    }

    static class ArtistDto {
        private Integer id;
        private String name;

        Artist toEntity() {
            Artist artist = new Artist();
            artist.setId(id);
            artist.setName(name);
            return artist;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
