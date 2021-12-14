package com.ferzerkerx.albumfinder.domain;

import com.ferzerkerx.albumfinder.infrastructure.entity.AlbumEntity;
import com.ferzerkerx.albumfinder.infrastructure.entity.ArtistEntity;

import java.util.Collection;
import java.util.List;

public interface AlbumFinderService {
    void deleteAlbumById(int recordId);

    void deleteArtistWithAlbumsById(int artistId);

    Collection<ArtistEntity> findAllArtists();

    ArtistEntity findArtistById(int artistId);

    List<ArtistEntity> findMatchedArtistsByName(String name);

    List<AlbumEntity> findMatchedAlbumByCriteria(String title, String year);

    AlbumEntity findAlbumById(int recordId);

    List<AlbumEntity> findAlbumsByArtist(int artistId);

    void saveArtist(ArtistEntity artistEntity);

    void saveAlbum(int artistId, AlbumEntity albumEntity);

    ArtistEntity updateArtist(ArtistEntity artistEntity);

    AlbumEntity updateAlbum(AlbumEntity albumEntity);
}
