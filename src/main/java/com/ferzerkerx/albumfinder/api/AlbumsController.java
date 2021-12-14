package com.ferzerkerx.albumfinder.api;

import com.ferzerkerx.albumfinder.domain.AlbumFinderService;
import com.ferzerkerx.albumfinder.infrastructure.entity.AlbumEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ferzerkerx.albumfinder.api.ResponseUtils.data;
import static com.ferzerkerx.albumfinder.api.ResponseUtils.status;

@RestController
public class AlbumsController {

    private final AlbumFinderService albumFinderService;

    public AlbumsController(AlbumFinderService albumFinderService) {
        this.albumFinderService = albumFinderService;
    }

    @GetMapping(value = {"/artist/{id}/albums"})
    public ResponseEntity<Response<List<AlbumEntity>>> showRecordsForArtist(@PathVariable(value = "id") int artistId) {
        return data(albumFinderService.findAlbumsByArtist(artistId));
    }

    @PostMapping(value = {"/admin/artist/{id}/album"})
    public ResponseEntity<Response<AlbumEntity>> saveAlbum(@PathVariable(value = "id") int artistId, @RequestBody AlbumDto albumDto) {
        AlbumEntity albumEntity = albumDto.toEntity();
        albumFinderService.saveAlbum(artistId, albumEntity);
        return data(albumEntity);
    }

    @GetMapping(value = {"/album/{id}"})
    public ResponseEntity<Response<AlbumEntity>> findAlbumById(@PathVariable(value = "id") int recordId) {
        return data(albumFinderService.findAlbumById(recordId));
    }

    @DeleteMapping(value = {"/admin/album/{id}"})
    public ResponseEntity<Response<Object>> deleteAlbumById(@PathVariable(value = "id") int albumId) {
        albumFinderService.deleteAlbumById(albumId);
        return status(HttpStatus.OK);
    }

    @PutMapping(value = {"/admin/album/{id}"})
    public ResponseEntity<Response<AlbumEntity>> updateAlbumById(@PathVariable(value = "id") int albumId, @RequestBody AlbumDto albumDto) {
        albumDto.setId(albumId);
        AlbumEntity albumEntity = albumDto.toEntity();
        AlbumEntity updatedAlbumEntity = albumFinderService.updateAlbum(albumEntity);
        return data(updatedAlbumEntity);
    }

    @GetMapping(value = {"/albums/search"})
    public ResponseEntity<Response<List<AlbumEntity>>> findMatchedRecordByCriteria(
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

        AlbumEntity toEntity() {
            AlbumEntity albumEntity = new AlbumEntity();
            albumEntity.setId(id);
            albumEntity.setTitle(title);
            albumEntity.setYear(year);
            return albumEntity;
        }
    }
}
