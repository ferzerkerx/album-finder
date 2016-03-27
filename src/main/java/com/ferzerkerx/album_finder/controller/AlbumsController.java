package com.ferzerkerx.album_finder.controller;

import java.util.List;
import com.ferzerkerx.album_finder.model.Album;
import com.ferzerkerx.album_finder.model.BaseException;
import com.ferzerkerx.album_finder.model.Response;
import com.ferzerkerx.album_finder.service.AlbumFinderService;
import com.ferzerkerx.album_finder.service.ResponseEntityService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlbumsController extends AbstractController {

    private final AlbumFinderService albumFinderService;

    @Autowired
    public AlbumsController(AlbumFinderService albumFinderService, ResponseEntityService responseEntityService) {
        super(responseEntityService);
        this.albumFinderService = albumFinderService;
    }

    @RequestMapping(value = {"/artist/{id}/albums"}, method = RequestMethod.GET)
    public ResponseEntity<Response<List<Album>>> showRecordsForArtist(@PathVariable(value = "id") int artistId) {
        return data(albumFinderService.findAlbumsByArtist(artistId));
    }

    @RequestMapping(value = {"/admin/artist/{id}/album"}, method = RequestMethod.POST)
    public ResponseEntity<Response<Album>> saveAlbum(@PathVariable(value = "id") int artistId, @RequestBody Album album) {
        albumFinderService.saveAlbum(artistId, album);
        return data(album);
    }

    @RequestMapping(value = {"/album/{id}"}, method = RequestMethod.GET)
    public ResponseEntity<Response<Album>> findAlbumById(@PathVariable(value = "id") int recordId) {
        return data(albumFinderService.findAlbumById(recordId));
    }

    @RequestMapping(value = {"/admin/album/{id}"}, method = RequestMethod.DELETE)
    public ResponseEntity<Response<Object>> deleteAlbumById(@PathVariable(value = "id") int albumId) {
        albumFinderService.deleteAlbumById(albumId);
        return status(HttpStatus.OK);
    }

    @RequestMapping(value = {"/admin/album/{id}"}, method = RequestMethod.PUT)
    public ResponseEntity<Response<Album>> updateAlbumById(@PathVariable(value = "id") int albumId, @RequestBody Album album) {
        album.setId(albumId);
        return data(albumFinderService.updateAlbum(album));
    }

    @RequestMapping(value = {"/albums/search"}, method = RequestMethod.GET)
    public ResponseEntity<Response<List<Album>>> findMatchedRecordByCriteria(
        @RequestParam(value = "title", required = false) String title,
        @RequestParam(value = "year", required = false) String year) {

        if (StringUtils.isBlank(title) && StringUtils.isBlank(year)) {
            throw new BaseException("At least one search criteria must be specified");
        }
        return data(albumFinderService.findMatchedAlbumByCriteria(title, year));
    }
}
