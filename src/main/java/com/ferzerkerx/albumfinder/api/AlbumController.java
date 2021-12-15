package com.ferzerkerx.albumfinder.api;

import com.ferzerkerx.albumfinder.api.dto.AlbumDto;
import com.ferzerkerx.albumfinder.api.dto.Response;
import com.ferzerkerx.albumfinder.api.dto.UpdateAlbumRequestDto;
import com.ferzerkerx.albumfinder.domain.Album;
import com.ferzerkerx.albumfinder.domain.AlbumFinderService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.ferzerkerx.albumfinder.api.ResponseUtils.data;

@RestController
@RequiredArgsConstructor
public class AlbumController {

    @NonNull
    private final AlbumFinderService albumFinderService;

    @GetMapping(value = {"/artist/{id}/albums"})
    public ResponseEntity<Response<List<AlbumDto>>> albumsByArtist(@PathVariable(value = "id") int artistId) {
        return data(
                albumFinderService.findAlbumsByArtist(artistId).stream()
                        .map(AlbumDto::of)
                        .toList()
        );
    }

    @PostMapping(value = {"/admin/artist/{id}/album"})
    public ResponseEntity<Response<AlbumDto>> saveAlbum(@PathVariable(value = "id") int artistId,
                                                        @RequestBody UpdateAlbumRequestDto updateAlbumRequestDto) {

        final Album album = updateAlbumRequestDto.toAlbum(artistId);
        final Album savedAlbum = albumFinderService.saveAlbum(album);
        return data(AlbumDto.of(savedAlbum));
    }

    @GetMapping(value = {"/album/{id}"})
    public ResponseEntity<Response<AlbumDto>> findAlbumById(@PathVariable(value = "id") int albumId) {
        return data(AlbumDto.of(albumFinderService.findAlbumById(albumId)));
    }

    @DeleteMapping(value = {"/admin/album/{id}"})
    public ResponseEntity<Response<Object>> deleteAlbumById(@PathVariable(value = "id") int albumId) {
        albumFinderService.deleteAlbumById(albumId);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = {"/admin/album/{id}"})
    public ResponseEntity<Response<AlbumDto>> updateAlbumById(@PathVariable(value = "id") int albumId,
                                                              @Valid @RequestBody UpdateAlbumRequestDto updateAlbumRequestDto) {

        Album album = albumFinderService.updateAlbum(
                updateAlbumRequestDto.toBuilder()
                        .id(albumId)
                        .build()
                        .toAlbum()
        );
        return data(AlbumDto.of(album));
    }

    @GetMapping(value = {"/albums/search"})
    public ResponseEntity<Response<List<AlbumDto>>> findAlbumByCriteria(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "year", required = false) String year) {

        return data(
                albumFinderService.findAlbumByCriteria(
                                Optional.ofNullable(title).orElse(StringUtils.EMPTY),
                                Optional.ofNullable(year).orElse(StringUtils.EMPTY)
                        ).stream()
                        .map(AlbumDto::of)
                        .toList()
        );
    }

}
