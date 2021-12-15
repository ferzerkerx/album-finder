package com.ferzerkerx.albumfinder.api;

import com.ferzerkerx.albumfinder.domain.Album;
import com.ferzerkerx.albumfinder.domain.Artist;

class UpdateAlbumRequestDto {
    private Integer id;
    private String title;
    private String year;

    public UpdateAlbumRequestDto(Integer id, String title, String year) {
        this.id = id;
        this.title = title;
        this.year = year;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(String year) {
        this.year = year;
    }

    Album toAlbum() {
        return new Album(id, title, year, null);
    }

    Album toAlbum(Artist artist) {
        return new Album(id, title, year, artist);
    }

}
