package com.ferzerkerx.albumfinder.api;

import com.ferzerkerx.albumfinder.domain.Artist;

class UpsertArtistRequestDto {
    private Integer id;
    private String name;

    Artist toArtist() {
        return new Artist(id, name);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
