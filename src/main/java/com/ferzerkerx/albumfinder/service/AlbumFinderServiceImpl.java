package com.ferzerkerx.albumfinder.service;

import com.ferzerkerx.albumfinder.model.Album;
import com.ferzerkerx.albumfinder.model.Artist;
import com.ferzerkerx.albumfinder.repository.AlbumRepository;
import com.ferzerkerx.albumfinder.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Transactional
@Service
public class AlbumFinderServiceImpl implements AlbumFinderService {
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumDao;

    @Autowired
    public AlbumFinderServiceImpl(ArtistRepository artistRepository, AlbumRepository albumDao) {
        this.artistRepository = artistRepository;
        this.albumDao = albumDao;
    }

    @Override
    public void deleteAlbumById(int recordId) {
        albumDao.deleteById(recordId);
    }

    @Override
    public void deleteArtistWithAlbumsById(int artistId) {
        albumDao.deleteRecordsByArtistId(artistId);
        artistRepository.deleteById(artistId);
    }

    @Override
    public Collection<Artist> findAllArtists() {
        return artistRepository.findAllArtists();
    }

    @Override
    public Artist findArtistById(int artistId) {
        return artistRepository.findById(artistId);
    }

    @Override
    public List<Artist> findMatchedArtistsByName(String name) {
        return artistRepository.findMatchedArtistsByName(name);
    }

    @Override
    public List<Album> findMatchedAlbumByCriteria(String title, String year) {
        Album album = new Album();
        album.setTitle(title);
        album.setYear(year);
        return albumDao.findByCriteria(album);
    }

    @Override
    public Album findAlbumById(int recordId) {
        return albumDao.findById(recordId);
    }

    @Override
    public List<Album> findAlbumsByArtist(int artistId) {
        return albumDao.findRecordsByArtist(artistId);
    }

    @Override
    public void saveArtist(Artist artist) {
        artistRepository.insert(artist);
    }

    @Override
    public void saveAlbum(int artistId, Album album) {
        Artist artist = new Artist();
        artist.setId(artistId);

        album.setArtist(artist);
        albumDao.insert(album);
    }

    @Override
    public Artist updateArtist(Artist artist) {
        return artistRepository.update(artist);
    }

    @Override
    public Album updateAlbum(Album album) {
        return albumDao.update(album);
    }
}
