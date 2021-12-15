package com.ferzerkerx.albumfinder.api;

import com.ferzerkerx.albumfinder.domain.AlbumFinderService;
import com.ferzerkerx.albumfinder.domain.Artist;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.ferzerkerx.albumfinder.api.ResponseUtils.data;

@RestController
public class ArtistController {

    private final AlbumFinderService albumFinderService;

    protected ArtistController(AlbumFinderService albumFinderService) {
        this.albumFinderService = albumFinderService;
    }

    @PostMapping(value = {"/admin/artist"})
    public ResponseEntity<Response<ArtistDto>> saveArtist(@RequestBody UpsertArtistRequestDto upsertArtistRequestDto) {
        Artist artist = upsertArtistRequestDto.toArtist();
        return data(ArtistDto.of(albumFinderService.saveArtist(artist)));
    }

    @GetMapping(value = {"/artist/{id}"})
    public ResponseEntity<Response<ArtistDto>> findArtistById(@PathVariable(value = "id") int artistId) {
        return data(ArtistDto.of(albumFinderService.findArtistById(artistId)));
    }

    @DeleteMapping(value = {"/admin/artist/{id}"})
    public void deleteArtistById(@PathVariable(value = "id") int artistId) {
        albumFinderService.deleteArtistById(artistId);
    }

    @PutMapping(value = {"/admin/artist/{id}"})
    public ResponseEntity<Response<ArtistDto>> updateArtistById(@PathVariable(value = "id") int artistId, @RequestBody UpsertArtistRequestDto upsertArtistRequestDto) {
        upsertArtistRequestDto.setId(artistId);
        Artist updatedArtistEntity = albumFinderService.updateArtist(upsertArtistRequestDto.toArtist());
        return data(ArtistDto.of(updatedArtistEntity));
    }

    @GetMapping(value = {"/artist/search"})
    public ResponseEntity<Response<List<ArtistDto>>> findMatchedArtistsByName(@RequestParam(value = "name") String name) {
        List<Artist> matchedArtistsByName = albumFinderService.findArtistsByName(name);
        return data(
                matchedArtistsByName.stream()
                        .map(ArtistDto::of)
                        .collect(Collectors.toList())
        );
    }

}
