package com.ferzerkerx.album_finder.service;

import java.util.Collection;
import java.util.List;
import com.ferzerkerx.album_finder.dao.AlbumDao;
import com.ferzerkerx.album_finder.dao.ArtistDao;
import com.ferzerkerx.album_finder.model.Album;
import com.ferzerkerx.album_finder.model.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AlbumFinderServiceImpl implements AlbumFinderService {
    private final ArtistDao artistDao;
    private final AlbumDao albumDao;

    @Autowired
    public AlbumFinderServiceImpl(ArtistDao artistDao, AlbumDao albumDao) {
        this.artistDao = artistDao;
        this.albumDao = albumDao;
    }

    @Override
    public void deleteAlbumById(int recordId) {
        albumDao.deleteById(recordId);
    }

    @Override
    public void deleteArtistWithAlbumsById(int artistId) {
        //This could be done using a cascade delete on the db
        albumDao.deleteRecordsByArtistId(artistId);
        artistDao.deleteById(artistId);
    }

    @Override
    public Collection<Artist> findAllArtists() {
        return artistDao.findAllArtists();
    }

    @Override
    public Artist findArtistById(int artistId) {
        return artistDao.findById(artistId);
    }

    @Override
    public List<Artist> findMatchedArtistsByName(String name) {
        return artistDao.findMatchedArtistsByName(name);
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
        artistDao.insert(artist);
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
        return artistDao.update(artist);
    }

    @Override
    public Album updateAlbum(Album album) {
        return albumDao.update(album);
    }
}
