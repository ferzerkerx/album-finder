package com.ferzerkerx.album_finder.service;

import java.util.Collection;
import java.util.List;
import com.ferzerkerx.album_finder.model.Album;
import com.ferzerkerx.album_finder.model.Artist;

public interface AlbumFinderService {
    void deleteAlbumById(int recordId);

    void deleteArtistWithAlbumsById(int artistId);

    Collection<Artist> findAllArtists();

    Artist findArtistById(int artistId);

    List<Artist> findMatchedArtistsByName(String name);

    List<Album> findMatchedRecordByCriteria(String title, String year);

    Album findAlbumById(int recordId);

    List<Album> findAlbumsByArtist(int artistId);

    void saveArtist(Artist artist);

    void saveAlbum(int artistId, Album album);

    Artist updateArtistById(Artist artist);

    Album updateAlbumById(Album album);
}
