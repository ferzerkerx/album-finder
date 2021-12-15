package com.ferzerkerx.albumfinder.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity(name = "Artist")
@Table(name = "artist")
public class ArtistEntity {

    @Id
    @SequenceGenerator(name = "pk_sequence", sequenceName = "artist_artist_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
    @Column(name = "artist_id")
    private Integer id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "artist", cascade = {CascadeType.DETACH}, fetch = FetchType.LAZY)
    private List<AlbumEntity> albums;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AlbumEntity> getAlbums() {
        return albums;
    }

    public void setAlbums(List<AlbumEntity> albums) {
        this.albums = albums;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ArtistEntity artistEntity = (ArtistEntity) o;

        return new EqualsBuilder()
                .append(id, artistEntity.id)
                .append(name, artistEntity.name)
                .append(albums, artistEntity.albums)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(albums)
                .toHashCode();
    }
}
