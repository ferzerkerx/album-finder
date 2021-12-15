package com.ferzerkerx.albumfinder.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ferzerkerx.albumfinder.domain.Album;
import lombok.NonNull;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record AlbumDto(@NonNull Integer id, @NonNull String title, @NonNull String year) {

    public static AlbumDto of(@NonNull Album album) {
        return new AlbumDto(album.id(), album.title(), album.year());
    }
}
