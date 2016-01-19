package com.ferzerkerx.album_finder.controller;

import java.util.Collection;
import java.util.List;
import com.ferzerkerx.album_finder.model.Artist;
import com.ferzerkerx.album_finder.service.AlbumFinderService;
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
public class ArtistController {
    
    @Autowired
    private AlbumFinderService albumFinderService;
    

    @RequestMapping(value = {"/artists"}, method = RequestMethod.GET)
    public Collection<Artist> showAllArtists() {
        return albumFinderService.findAllArtists();
    }


    @RequestMapping(value = {"/admin/artists"}, method = RequestMethod.POST)
    public Artist saveArtist(@RequestBody Artist artist) {
        albumFinderService.saveArtist(artist);
        return artist;
    }

    @RequestMapping(value = {"/artist/{id}"}, method = RequestMethod.GET)
    public Artist findArtistById(@PathVariable(value = "id") int artistId) {
        return albumFinderService.findArtistById(artistId);
    }

    @RequestMapping(value = {"/admin/artist/{id}"}, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteArtistById(@PathVariable(value = "id") int artistId) {
        albumFinderService.deleteArtistWithAlbumsById(artistId);
    }

    @RequestMapping(value = {"/admin/artist/{id}"}, method = RequestMethod.PUT)
    public Artist updateArtistById(@PathVariable(value = "id") int artistId, @RequestBody Artist artist) {
        artist.setId(artistId);
        return albumFinderService.updateArtistById(artist);
    }

    @RequestMapping(value = {"/artist/search"}, method = RequestMethod.GET)
    public List<Artist> findMatchedArtistsByName(@RequestParam(value = "name") String name) {
        return albumFinderService.findMatchedArtistsByName(name);
    }
}
