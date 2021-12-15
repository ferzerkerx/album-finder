package com.ferzerkerx.albumfinder.api;

import com.ferzerkerx.albumfinder.domain.Album;

class UpdateAlbumRequestDto {
    private Integer id;
    private String title;
    private String year;

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
}
