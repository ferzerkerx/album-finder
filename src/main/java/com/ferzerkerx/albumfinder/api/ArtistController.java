package com.ferzerkerx.albumfinder.api;

import com.ferzerkerx.albumfinder.api.dto.ArtistDto;
import com.ferzerkerx.albumfinder.api.dto.Response;
import com.ferzerkerx.albumfinder.api.dto.UpsertArtistRequestDto;
import com.ferzerkerx.albumfinder.domain.AlbumFinderService;
import com.ferzerkerx.albumfinder.domain.Artist;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.ferzerkerx.albumfinder.api.ResponseUtils.data;

@RestController
@RequiredArgsConstructor
public class ArtistController {

    @NonNull
    private final AlbumFinderService albumFinderService;

    @PostMapping(value = {"/admin/artist"})
    public ResponseEntity<Response<ArtistDto>> saveArtist(@Valid @RequestBody UpsertArtistRequestDto upsertArtistRequestDto) {
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
    public ResponseEntity<Response<ArtistDto>> updateArtistById(@PathVariable(value = "id") int artistId,
                                                                @RequestBody UpsertArtistRequestDto upsertArtistRequestDto) {
        Artist updatedArtistEntity = albumFinderService.updateArtist(
                upsertArtistRequestDto.toBuilder()
                        .id(artistId)
                        .build()
                        .toArtist()
        );
        return data(ArtistDto.of(updatedArtistEntity));
    }

    @GetMapping(value = {"/artist/search"})
    public ResponseEntity<Response<List<ArtistDto>>> findMatchedArtistsByName(@RequestParam(value = "name") String name) {
        List<Artist> matchedArtistsByName = albumFinderService.findArtistsByName(name);
        return data(
                matchedArtistsByName.stream()
                        .map(ArtistDto::of)
                        .toList()
        );
    }

}
