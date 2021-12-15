package com.ferzerkerx.albumfinder.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ferzerkerx.albumfinder.domain.Album;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlbumDto {
    private final Integer id;
    private final String title;
    private final String year;

    public AlbumDto(Integer id, String title, String year) {
        this.id = id;
        this.title = title;
        this.year = year;
    }

    public static AlbumDto of(Album album) {
        return new AlbumDto(album.id(), album.title(), album.year());
    }

}
