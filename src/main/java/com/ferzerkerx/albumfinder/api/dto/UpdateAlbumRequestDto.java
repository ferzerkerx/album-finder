package com.ferzerkerx.albumfinder.api.dto;

import com.ferzerkerx.albumfinder.domain.Album;
import com.ferzerkerx.albumfinder.domain.Artist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public
class UpdateAlbumRequestDto {
    @NotNull
    private final Integer id;
    @NotBlank
    private final String title;
    @NotBlank
    private final String year;

    public Album toAlbum() {
        return new Album(id, title, year, null);
    }

    public Album toAlbum(int artistId) {
        return new Album(id, title, year, new Artist(artistId, StringUtils.EMPTY));
    }

}
