package com.ferzerkerx.albumfinder.api;

import com.ferzerkerx.albumfinder.domain.AlbumFinderService;
import com.ferzerkerx.albumfinder.domain.Artist;
import com.ferzerkerx.albumfinder.infrastructure.entity.ArtistEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ferzerkerx.albumfinder.api.ResponseUtils.data;

@RestController
public class ArtistController {

    private final AlbumFinderService albumFinderService;

    protected ArtistController(AlbumFinderService albumFinderService) {
        this.albumFinderService = albumFinderService;
    }

    @PostMapping(value = {"/admin/artist"})
    public ResponseEntity<Response<Artist>> saveArtist(@RequestBody UpsertArtistRequestDto upsertArtistRequestDto) {
        Artist artist = upsertArtistRequestDto.toArtist();
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
    public ResponseEntity<Response<Artist>> updateArtistById(@PathVariable(value = "id") int artistId, @RequestBody UpsertArtistRequestDto upsertArtistRequestDto) {
        upsertArtistRequestDto.setId(artistId);
        Artist updateArtistEntity = albumFinderService.updateArtist(upsertArtistRequestDto.toArtist());
        return data(updateArtistEntity);
    }

    @GetMapping(value = {"/artist/search"})
    public ResponseEntity<Response<List<Artist>>> findMatchedArtistsByName(@RequestParam(value = "name") String name) {
        List<Artist> matchedArtistsByName = albumFinderService.findArtistsByName(name);
        return data(matchedArtistsByName);
    }

    static class UpsertArtistRequestDto {
        private Integer id;
        private String name;

        ArtistEntity toEntity() {
            ArtistEntity artistEntity = new ArtistEntity();
            artistEntity.setId(id);
            artistEntity.setName(name);
            return artistEntity;
        }

        Artist toArtist() {
            return new Artist(id, name);
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
