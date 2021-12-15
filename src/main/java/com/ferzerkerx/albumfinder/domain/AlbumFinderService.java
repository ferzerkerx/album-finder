package com.ferzerkerx.albumfinder.domain;

import lombok.NonNull;

import java.util.List;

public interface AlbumFinderService {

    void deleteAlbumById(int albumId);

    void deleteArtistById(int artistId);

    List<Artist> findAllArtists();

    Artist findArtistById(int artistId);

    List<Artist> findArtistsByName(@NonNull String name);

    List<Album> findAlbumByCriteria(@NonNull String title, @NonNull String year);

    Album findAlbumById(int albumId);

    List<Album> findAlbumsByArtist(int artistId);

    Artist saveArtist(@NonNull Artist artist);

    Album saveAlbum(@NonNull Album album);

    Artist updateArtist(@NonNull Artist artist);

    Album updateAlbum(@NonNull Album album);
}
