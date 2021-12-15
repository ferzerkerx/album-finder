package com.ferzerkerx.albumfinder.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ferzerkerx.albumfinder.domain.Artist;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record ArtistDto(Integer id, String name) {

    static ArtistDto of(Artist artist) {
        return new ArtistDto(artist.id(), artist.name());
    }
}
