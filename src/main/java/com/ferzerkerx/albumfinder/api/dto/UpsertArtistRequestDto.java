package com.ferzerkerx.albumfinder.api.dto;

import com.ferzerkerx.albumfinder.domain.Artist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class UpsertArtistRequestDto {
    @NotNull
    private final Integer id;
    @NotBlank
    private final String name;

    public Artist toArtist() {
        return new Artist(id, name);
    }
}
