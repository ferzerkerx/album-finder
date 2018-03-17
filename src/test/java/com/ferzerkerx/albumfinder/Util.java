package com.ferzerkerx.albumfinder;

import com.ferzerkerx.albumfinder.model.Album;
import com.ferzerkerx.albumfinder.model.Artist;

public final class Util {


    private Util() {
        throw new AssertionError();
    }

    public static Artist createArtist() {
        Artist artist = new Artist();
        artist.setName("someArtist");
        artist.setId(1);
        return artist;
    }

    public static Album createAlbum(Artist artist) {
        Album album = createAlbum();
        album.setArtist(artist);
        return album;
    }

    public static Album createAlbum() {
        Album album = new Album();
        album.setTitle("some title");
        album.setYear("2016");
        album.setId(1);
        return album;
    }
}
