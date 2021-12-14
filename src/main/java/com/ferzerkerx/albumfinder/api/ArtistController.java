package com.ferzerkerx.albumfinder.api;

import com.ferzerkerx.albumfinder.domain.AlbumFinderService;
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
    public ResponseEntity<Response<ArtistEntity>> saveArtist(@RequestBody ArtistDto artistDto) {
        ArtistEntity artistEntity = artistDto.toEntity();
        albumFinderService.saveArtist(artistEntity);
        return data(artistEntity);
    }

    @GetMapping(value = {"/artist/{id}"})
    public ResponseEntity<Response<ArtistEntity>> findArtistById(@PathVariable(value = "id") int artistId) {
        return data(albumFinderService.findArtistById(artistId));
    }

    @DeleteMapping(value = {"/admin/artist/{id}"})
    public void deleteArtistById(@PathVariable(value = "id") int artistId) {
        albumFinderService.deleteArtistWithAlbumsById(artistId);
    }

    @PutMapping(value = {"/admin/artist/{id}"})
    public ResponseEntity<Response<ArtistEntity>> updateArtistById(@PathVariable(value = "id") int artistId, @RequestBody ArtistDto artistDto) {
        artistDto.setId(artistId);
        ArtistEntity updateArtistEntity = albumFinderService.updateArtist(artistDto.toEntity());
        return data(updateArtistEntity);
    }

    @GetMapping(value = {"/artist/search"})
    public ResponseEntity<Response<List<ArtistEntity>>> findMatchedArtistsByName(@RequestParam(value = "name") String name) {
        List<ArtistEntity> matchedArtistsByName = albumFinderService.findMatchedArtistsByName(name);
        return data(matchedArtistsByName);
    }

    static class ArtistDto {
        private Integer id;
        private String name;

        ArtistEntity toEntity() {
            ArtistEntity artistEntity = new ArtistEntity();
            artistEntity.setId(id);
            artistEntity.setName(name);
            return artistEntity;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
