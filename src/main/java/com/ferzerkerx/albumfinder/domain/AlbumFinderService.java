package com.ferzerkerx.albumfinder.domain;

import java.util.List;

public interface AlbumFinderService {
    void deleteAlbumById(int recordId);

    void deleteArtistById(int artistId);

    List<Artist> findAllArtists();

    Artist findArtistById(int artistId);

    List<Artist> findArtistsByName(String name);

    List<Album> findMatchedAlbumByCriteria(String title, String year);

    Album findAlbumById(int recordId);

    List<Album> findAlbumsByArtist(int artistId);

    Artist saveArtist(Artist Artist);

    void saveAlbum(int artistId, Album Album);

    Artist updateArtist(Artist Artist);

    Album updateAlbum(Album Album);
}
