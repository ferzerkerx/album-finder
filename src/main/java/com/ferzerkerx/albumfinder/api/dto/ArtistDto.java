package com.ferzerkerx.albumfinder.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ferzerkerx.albumfinder.domain.Artist;
import lombok.NonNull;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record ArtistDto(@NonNull Integer id, @NonNull String name) {

    public static ArtistDto of(@NonNull Artist artist) {
        return new ArtistDto(artist.id(), artist.name());
    }
}
