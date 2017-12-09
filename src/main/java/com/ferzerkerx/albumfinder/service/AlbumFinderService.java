package com.ferzerkerx.albumfinder.service;

import java.util.Collection;
import java.util.List;
import com.ferzerkerx.albumfinder.model.Album;
import com.ferzerkerx.albumfinder.model.Artist;

public interface AlbumFinderService {
    void deleteAlbumById(int recordId);

    void deleteArtistWithAlbumsById(int artistId);

    Collection<Artist> findAllArtists();

    Artist findArtistById(int artistId);

    List<Artist> findMatchedArtistsByName(String name);

    List<Album> findMatchedAlbumByCriteria(String title, String year);

    Album findAlbumById(int recordId);

    List<Album> findAlbumsByArtist(int artistId);

    void saveArtist(Artist artist);

    void saveAlbum(int artistId, Album album);

    Artist updateArtist(Artist artist);

    Album updateAlbum(Album album);
}
