package com.ferzerkerx.albumfinder.infrastructure;

import com.ferzerkerx.albumfinder.domain.AlbumFinderService;
import com.ferzerkerx.albumfinder.infrastructure.entity.AlbumEntity;
import com.ferzerkerx.albumfinder.infrastructure.entity.ArtistEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Transactional
@Service
public class AlbumFinderServiceImpl implements AlbumFinderService {
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;

    @Autowired
    public AlbumFinderServiceImpl(ArtistRepository artistRepository, AlbumRepository albumDao) {
        this.artistRepository = artistRepository;
        this.albumRepository = albumDao;
    }

    @Override
    public void deleteAlbumById(int recordId) {
        albumRepository.deleteById(recordId);
    }

    @Override
    public void deleteArtistWithAlbumsById(int artistId) {
        albumRepository.deleteRecordsByArtistId(artistId);
        artistRepository.deleteById(artistId);
    }

    @Override
    public Collection<ArtistEntity> findAllArtists() {
        return artistRepository.findAllArtists();
    }

    @Override
    public ArtistEntity findArtistById(int artistId) {
        return artistRepository.findById(artistId);
    }

    @Override
    public List<ArtistEntity> findMatchedArtistsByName(String name) {
        return artistRepository.findArtistsByName(name);
    }

    @Override
    public List<AlbumEntity> findMatchedAlbumByCriteria(String title, String year) {
        AlbumEntity albumEntity = new AlbumEntity();
        albumEntity.setTitle(title);
        albumEntity.setYear(year);
        return albumRepository.findByCriteria(albumEntity);
    }

    @Override
    public AlbumEntity findAlbumById(int recordId) {
        return albumRepository.findById(recordId);
    }

    @Override
    public List<AlbumEntity> findAlbumsByArtist(int artistId) {
        return albumRepository.findRecordsByArtist(artistId);
    }

    @Override
    public void saveArtist(ArtistEntity artistEntity) {
        artistRepository.insert(artistEntity);
    }

    @Override
    public void saveAlbum(int artistId, AlbumEntity albumEntity) {
        ArtistEntity artistEntity = new ArtistEntity();
        artistEntity.setId(artistId);

        albumEntity.setArtist(artistEntity);
        albumRepository.insert(albumEntity);
    }

    @Override
    public ArtistEntity updateArtist(ArtistEntity artistEntity) {
        return artistRepository.update(artistEntity);
    }

    @Override
    public AlbumEntity updateAlbum(AlbumEntity albumEntity) {
        return albumRepository.update(albumEntity);
    }
}
