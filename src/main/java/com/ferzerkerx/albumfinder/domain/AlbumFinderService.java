package com.ferzerkerx.albumfinder.domain;

import java.util.List;

public interface AlbumFinderService {
    void deleteAlbumById(int albumId);

    void deleteArtistById(int artistId);

    List<Artist> findAllArtists();

    Artist findArtistById(int artistId);

    List<Artist> findArtistsByName(String name);

    List<Album> findAlbumByCriteria(String title, String year);

    Album findAlbumById(int albumId);

    List<Album> findAlbumsByArtist(int artistId);

    Artist saveArtist(Artist artist);

    Album saveAlbum(Album album);

    Artist updateArtist(Artist artist);

    Album updateAlbum(Album album);
}
