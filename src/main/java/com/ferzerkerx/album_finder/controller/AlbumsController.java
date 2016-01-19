package com.ferzerkerx.album_finder.controller;

import java.util.List;
import com.ferzerkerx.album_finder.model.Album;
import com.ferzerkerx.album_finder.model.BaseException;
import com.ferzerkerx.album_finder.service.AlbumFinderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlbumsController {

    @Autowired
    private AlbumFinderService albumFinderService;

    @RequestMapping(value = {"/artist/{id}/albums"}, method = RequestMethod.GET)
    public List<Album> showRecordsForArtist(@PathVariable(value = "id") int artistId) {
        return albumFinderService.findAlbumsByArtist(artistId);
    }

    @RequestMapping(value = {"/admin/artist/{id}/album"}, method = RequestMethod.POST)
    public Album saveAlbum(@PathVariable(value = "id") int artistId, @RequestBody Album album) {
        albumFinderService.saveAlbum(artistId, album);
        return album;
    }

    @RequestMapping(value = {"/album/{id}"}, method = RequestMethod.GET)
    public Album findAlbumById(@PathVariable(value = "id") int recordId) {
        return albumFinderService.findAlbumById(recordId);
    }

    @RequestMapping(value = {"/admin/album/{id}"}, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteAlbumById(@PathVariable(value = "id") int albumId) {
        albumFinderService.deleteAlbumById(albumId);
    }

    @RequestMapping(value = {"/admin/album/{id}"}, method = RequestMethod.PUT)
    public Album updateAlbumById(@PathVariable(value = "id") int albumId, @RequestBody Album album) {
        album.setId(albumId);
        return albumFinderService.updateAlbumById(album);
    }

    @RequestMapping(value = {"/albums/search"}, method = RequestMethod.GET)
    public List<Album> findMatchedRecordByCriteria(
        @RequestParam(value = "title", required = false) String title,
        @RequestParam(value = "year", required = false) String year) {

        if (StringUtils.isBlank(title) && StringUtils.isBlank(year)) {
            throw new BaseException("At least one search criteria must be specified");
        }
        return albumFinderService.findMatchedRecordByCriteria(title, year);
    }
}
