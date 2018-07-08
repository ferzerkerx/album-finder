package com.ferzerkerx.albumfinder.controller;

import com.ferzerkerx.albumfinder.model.Album;
import com.ferzerkerx.albumfinder.model.Response;
import com.ferzerkerx.albumfinder.service.AlbumFinderService;
import com.ferzerkerx.albumfinder.service.ResponseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AlbumsController extends AbstractController {

    private final AlbumFinderService albumFinderService;

    @Autowired
    public AlbumsController(AlbumFinderService albumFinderService, ResponseEntityService responseEntityService) {
        super(responseEntityService);
        this.albumFinderService = albumFinderService;
    }

    @GetMapping(value = {"/artist/{id}/albums"})
    public ResponseEntity<Response<List<Album>>> showRecordsForArtist(@PathVariable(value = "id") int artistId) {
        return data(albumFinderService.findAlbumsByArtist(artistId));
    }

    @PostMapping(value = {"/admin/artist/{id}/album"})
    public ResponseEntity<Response<Album>> saveAlbum(@PathVariable(value = "id") int artistId, @RequestBody AlbumDto albumDto) {
        Album album = albumDto.toEntity();
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
        return status(HttpStatus.OK);
    }

    @PutMapping(value = {"/admin/album/{id}"})
    public ResponseEntity<Response<Album>> updateAlbumById(@PathVariable(value = "id") int albumId, @RequestBody AlbumDto albumDto) {
        albumDto.setId(albumId);
        Album album = albumDto.toEntity();
        Album updatedAlbum = albumFinderService.updateAlbum(album);
        return data(updatedAlbum);
    }

    @GetMapping(value = {"/albums/search"})
    public ResponseEntity<Response<List<Album>>> findMatchedRecordByCriteria(
        @RequestParam(value = "title", required = false) String title,
        @RequestParam(value = "year", required = false) String year) {

        return data(albumFinderService.findMatchedAlbumByCriteria(title, year));
    }

    static class AlbumDto {
        private Integer id;
        private String title;
        private String year;

        public void setId(Integer id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setYear(String year) {
            this.year = year;
        }

        Album toEntity() {
            Album album = new Album();
            album.setId(id);
            album.setTitle(title);
            album.setYear(year);
            return album;
        }
    }
}
