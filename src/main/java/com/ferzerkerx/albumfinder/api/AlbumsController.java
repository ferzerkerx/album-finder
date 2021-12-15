package com.ferzerkerx.albumfinder.api;

import com.ferzerkerx.albumfinder.domain.Album;
import com.ferzerkerx.albumfinder.domain.AlbumFinderService;
import com.ferzerkerx.albumfinder.domain.Artist;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.ferzerkerx.albumfinder.api.ResponseUtils.data;

@RestController
public class AlbumsController {

    private final AlbumFinderService albumFinderService;

    public AlbumsController(AlbumFinderService albumFinderService) {
        this.albumFinderService = albumFinderService;
    }

    @GetMapping(value = {"/artist/{id}/albums"})
    public ResponseEntity<Response<List<AlbumDto>>> showRecordsForArtist(@PathVariable(value = "id") int artistId) {
        return data(
                albumFinderService.findAlbumsByArtist(artistId).stream()
                        .map(AlbumDto::of)
                        .collect(Collectors.toList())
        );
    }

    @PostMapping(value = {"/admin/artist/{id}/album"})
    public ResponseEntity<Response<AlbumDto>> saveAlbum(@PathVariable(value = "id") int artistId,
                                                        @RequestBody UpdateAlbumRequestDto updateAlbumRequestDto) {

        final Album album = updateAlbumRequestDto.toAlbum(new Artist(artistId, null));
        final Album savedAlbum = albumFinderService.saveAlbum(album);
        return data(AlbumDto.of(savedAlbum));
    }

    @GetMapping(value = {"/album/{id}"})
    public ResponseEntity<Response<AlbumDto>> findAlbumById(@PathVariable(value = "id") int recordId) {
        return data(AlbumDto.of(albumFinderService.findAlbumById(recordId)));
    }

    @DeleteMapping(value = {"/admin/album/{id}"})
    public ResponseEntity<Response<Object>> deleteAlbumById(@PathVariable(value = "id") int albumId) {
        albumFinderService.deleteAlbumById(albumId);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = {"/admin/album/{id}"})
    public ResponseEntity<Response<AlbumDto>> updateAlbumById(@PathVariable(value = "id") int albumId,
                                                              @RequestBody UpdateAlbumRequestDto updateAlbumRequestDto) {
        updateAlbumRequestDto.setId(albumId);
        Album album = albumFinderService.updateAlbum(updateAlbumRequestDto.toAlbum());
        return data(AlbumDto.of(album));
    }

    @GetMapping(value = {"/albums/search"})
    public ResponseEntity<Response<List<AlbumDto>>> findMatchedRecordByCriteria(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "year", required = false) String year) {

        return data(
                albumFinderService.findMatchedAlbumByCriteria(title, year).stream()
                        .map(AlbumDto::of)
                        .collect(Collectors.toList())
        );
    }

}
