package com.ferzerkerx.albumfinder;

import com.ferzerkerx.albumfinder.domain.Album;
import com.ferzerkerx.albumfinder.domain.Artist;
import com.ferzerkerx.albumfinder.infrastructure.entity.AlbumEntity;
import com.ferzerkerx.albumfinder.infrastructure.entity.ArtistEntity;

public final class Fixtures {

    private Fixtures() {
        throw new AssertionError();
    }

    public static ArtistEntity artistEntity() {
        ArtistEntity artistEntity = new ArtistEntity();
        artistEntity.setName("someArtist");
        artistEntity.setId(1);
        return artistEntity;
    }

    public static AlbumEntity albumEntityOf(ArtistEntity artistEntity) {
        AlbumEntity albumEntity = albumEntity();
        albumEntity.setArtist(artistEntity);
        return albumEntity;
    }

    public static AlbumEntity albumEntity() {
        AlbumEntity albumEntity = new AlbumEntity();
        albumEntity.setTitle("some title");
        albumEntity.setYear("2016");
        albumEntity.setId(1);
        return albumEntity;
    }

    public static Album album() {
        return new Album(1, "some title", "2016", artist());
    }

    public static Artist artist() {
        return new Artist(1, "someArtist");
    }
}
