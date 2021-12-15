package com.ferzerkerx.albumfinder.infrastructure;

import com.ferzerkerx.albumfinder.domain.Album;
import com.ferzerkerx.albumfinder.domain.AlbumFinderService;
import com.ferzerkerx.albumfinder.domain.Artist;
import com.ferzerkerx.albumfinder.infrastructure.entity.AlbumEntity;
import com.ferzerkerx.albumfinder.infrastructure.entity.ArtistEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.ferzerkerx.albumfinder.infrastructure.AlbumFinderServiceImpl.Converter.*;

@Transactional
@Service
@RequiredArgsConstructor
public class AlbumFinderServiceImpl implements AlbumFinderService {
    @NonNull
    private final ArtistRepository artistRepository;
    @NonNull
    private final AlbumRepository albumRepository;

    @Override
    public void deleteAlbumById(int albumId) {
        albumRepository.deleteById(albumId);
    }

    @Override
    public void deleteArtistById(int artistId) {
        albumRepository.deleteAlbumsByArtistId(artistId);
        artistRepository.deleteById(artistId);
    }

    @Override
    public List<Artist> findAllArtists() {
        return artistRepository.findAllArtists().stream()
                .map(Converter::toArtist)
                .toList();
    }

    @Override
    public Artist findArtistById(int artistId) {
        return toArtist(artistRepository.findById(artistId));
    }

    @Override
    public List<Artist> findArtistsByName(@NonNull String name) {
        return artistRepository.findArtistsByName(name).stream()
                .map(Converter::toArtist)
                .toList();
    }

    @Override
    public List<Album> findAlbumByCriteria(@NonNull String title, @NonNull String year) {
        AlbumEntity albumEntity = new AlbumEntity();
        albumEntity.setTitle(title);
        albumEntity.setYear(year);
        return albumRepository.findByCriteria(albumEntity).stream()
                .map(Converter::toAlbum)
                .toList();
    }

    @Override
    public Album findAlbumById(int albumId) {
        return toAlbum(albumRepository.findById(albumId));
    }

    @Override
    public List<Album> findAlbumsByArtist(int artistId) {
        return albumRepository.findAlbumsByArtist(artistId).stream()
                .map(Converter::toAlbum)
                .toList();
    }

    @Override
    public Artist saveArtist(@NonNull Artist artist) {
        final ArtistEntity artistEntity = toArtistEntity(artist);
        artistRepository.insert(artistEntity);
        return toArtist(artistEntity);
    }

    @Override
    public Album saveAlbum(@NonNull Album album) {
        final AlbumEntity albumEntity = toAlbumEntity(album);
        albumRepository.insert(albumEntity);
        return toAlbum(albumEntity);
    }

    @Override
    public Artist updateArtist(@NonNull Artist artist) {
        final ArtistEntity updatedArtistEntity = artistRepository.update(toArtistEntity(artist));
        return toArtist(updatedArtistEntity);
    }

    @Override
    public Album updateAlbum(@NonNull Album album) {
        final AlbumEntity updatedAlbumEntity = albumRepository.update(toAlbumEntity(album));
        return toAlbum(updatedAlbumEntity);
    }

    @UtilityClass
    static class Converter {

        static AlbumEntity toAlbumEntity(@NonNull Album album) {
            final AlbumEntity albumEntity = new AlbumEntity();
            albumEntity.setId(album.id());
            albumEntity.setTitle(album.title());
            albumEntity.setYear(album.year());
            Optional.ofNullable(album.artist()).ifPresent(artist ->
                    albumEntity.setArtist(toArtistEntity(artist)));
            return albumEntity;
        }

        static Album toAlbum(@NonNull AlbumEntity albumEntity) {
            return new Album(albumEntity.getId(), albumEntity.getTitle(), albumEntity.getYear(), toArtist(albumEntity.getArtist()));
        }

        static ArtistEntity toArtistEntity(@NonNull Artist artist) {
            final ArtistEntity artistEntity = new ArtistEntity();
            artistEntity.setId(artist.id());
            artistEntity.setName(artist.name());
            return artistEntity;
        }

        static Artist toArtist(@NonNull ArtistEntity artistEntity) {
            return new Artist(artistEntity.getId(), artistEntity.getName());
        }
    }
}



















