package com.ferzerkerx.albumfinder.api;

import com.ferzerkerx.albumfinder.domain.Album;
import com.ferzerkerx.albumfinder.domain.AlbumFinderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ferzerkerx.albumfinder.api.ResponseUtils.data;

//TODO return albumdto
@RestController
public class AlbumsController {

    private final AlbumFinderService albumFinderService;

    public AlbumsController(AlbumFinderService albumFinderService) {
        this.albumFinderService = albumFinderService;
    }

    @GetMapping(value = {"/artist/{id}/albums"})
    public ResponseEntity<Response<List<Album>>> showRecordsForArtist(@PathVariable(value = "id") int artistId) {
        return data(albumFinderService.findAlbumsByArtist(artistId));
    }

    @PostMapping(value = {"/admin/artist/{id}/album"})
    public ResponseEntity<Response<Album>> saveAlbum(@PathVariable(value = "id") int artistId, @RequestBody UpdateAlbumRequestDto updateAlbumRequestDto) {
        Album album = updateAlbumRequestDto.toAlbum();
        albumFinderService.saveAlbum(artistId, album);
        return data(album);
    }

    @GetMapping(value = {"/album/{id}"})
    public ResponseEntity<Response<Album>> findAlbumById(@PathVariable(value = "id") int recordId) {
        return data(albumFinderService.findAlbumById(recordId));
    }

    @DeleteMapping(value = {"/admin/album/{id}"})
    public ResponseEntity<Response<Object>> deleteAlbumById(@PathVariable(value = "id") int albumId) {
        albumFinderService.deleteAlbumById(albumId);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = {"/admin/album/{id}"})
    public ResponseEntity<Response<Album>> updateAlbumById(@PathVariable(value = "id") int albumId, @RequestBody UpdateAlbumRequestDto updateAlbumRequestDto) {
        updateAlbumRequestDto.setId(albumId);
        Album albumEntity = updateAlbumRequestDto.toAlbum();
        Album updatedAlbumEntity = albumFinderService.updateAlbum(albumEntity);
        return data(updatedAlbumEntity);
    }

    @GetMapping(value = {"/albums/search"})
    public ResponseEntity<Response<List<Album>>> findMatchedRecordByCriteria(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "year", required = false) String year) {

        return data(albumFinderService.findMatchedAlbumByCriteria(title, year));
    }

}
