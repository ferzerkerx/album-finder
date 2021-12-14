package com.ferzerkerx.albumfinder;

import com.ferzerkerx.albumfinder.infrastructure.entity.AlbumEntity;
import com.ferzerkerx.albumfinder.infrastructure.entity.ArtistEntity;

public final class Fixtures {

    private Fixtures() {
        throw new AssertionError();
    }

    public static ArtistEntity createArtist() {
        ArtistEntity artistEntity = new ArtistEntity();
        artistEntity.setName("someArtist");
        artistEntity.setId(1);
        return artistEntity;
    }

    public static AlbumEntity createAlbum(ArtistEntity artistEntity) {
        AlbumEntity albumEntity = createAlbum();
        albumEntity.setArtist(artistEntity);
        return albumEntity;
    }

    public static AlbumEntity createAlbum() {
        AlbumEntity albumEntity = new AlbumEntity();
        albumEntity.setTitle("some title");
        albumEntity.setYear("2016");
        albumEntity.setId(1);
        return albumEntity;
    }
}
