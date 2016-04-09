package com.ferzerkerx.album_finder.controller;

import java.util.List;
import com.ferzerkerx.album_finder.model.Artist;
import com.ferzerkerx.album_finder.model.Response;
import com.ferzerkerx.album_finder.service.AlbumFinderService;
import com.ferzerkerx.album_finder.service.ResponseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArtistController extends AbstractController {

    private final AlbumFinderService albumFinderService;

    @Autowired
    protected ArtistController(ResponseEntityService responseEntityService, AlbumFinderService albumFinderService) {
        super(responseEntityService);
        this.albumFinderService = albumFinderService;
    }

    @RequestMapping(value = {"/admin/artist"}, method = RequestMethod.POST)
    public ResponseEntity<Response<Artist>> saveArtist(@RequestBody Artist artist) {
        albumFinderService.saveArtist(artist);
        return data(artist);
    }

    @RequestMapping(value = {"/artist/{id}"}, method = RequestMethod.GET)
    public ResponseEntity<Response<Artist>> findArtistById(@PathVariable(value = "id") int artistId) {
        return data(albumFinderService.findArtistById(artistId));
    }

    @RequestMapping(value = {"/admin/artist/{id}"}, method = RequestMethod.DELETE)
    public void deleteArtistById(@PathVariable(value = "id") int artistId) {
        albumFinderService.deleteArtistWithAlbumsById(artistId);
    }

    @RequestMapping(value = {"/admin/artist/{id}"}, method = RequestMethod.PUT)
    public ResponseEntity<Response<Artist>> updateArtistById(@PathVariable(value = "id") int artistId, @RequestBody Artist artist) {
        artist.setId(artistId);
        return data(albumFinderService.updateArtist(artist));
    }

    @RequestMapping(value = {"/artist/search"}, method = RequestMethod.GET)
    public ResponseEntity<Response<List<Artist>>> findMatchedArtistsByName(@RequestParam(value = "name") String name) {
        return data(albumFinderService.findMatchedArtistsByName(name));
    }
}
