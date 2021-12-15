package com.ferzerkerx.albumfinder.api;

import com.ferzerkerx.albumfinder.domain.Artist;

public class ArtistDto {
    private final Integer id;
    private final String name;

    public ArtistDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    static ArtistDto of(Artist artist) {
        return new ArtistDto(artist.id(), artist.name());
    }
}
