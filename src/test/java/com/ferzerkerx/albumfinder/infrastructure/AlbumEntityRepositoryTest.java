package com.ferzerkerx.albumfinder.infrastructure;

import com.ferzerkerx.albumfinder.DbIntegrationTest;
import com.ferzerkerx.albumfinder.infrastructure.entity.AlbumEntity;
import com.ferzerkerx.albumfinder.infrastructure.entity.ArtistEntity;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.List;

import static com.ferzerkerx.albumfinder.Fixtures.createAlbum;
import static com.ferzerkerx.albumfinder.Fixtures.createArtist;
import static org.junit.jupiter.api.Assertions.*;

class AlbumEntityRepositoryTest extends DbIntegrationTest {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistRepository artistRepository;

    private ArtistEntity artistEntity;

    @BeforeEach
    void setUp() {
        artistEntity = createArtist();
        artistRepository.insert(artistEntity);
    }

    @Test
    void deleteRecordsByArtistId() {
        AlbumEntity albumEntity = createAlbum(artistEntity);
        albumRepository.insert(albumEntity);

        flush();

        List<AlbumEntity> recordsByArtist = albumRepository.findRecordsByArtist(artistEntity.getId());
        assertNotNull(recordsByArtist);
        assertEquals(1, recordsByArtist.size());

        albumRepository.deleteRecordsByArtistId(artistEntity.getId());

        List<AlbumEntity> emptyByArtist = albumRepository.findRecordsByArtist(artistEntity.getId());
        assertNotNull(emptyByArtist);
        assertTrue(emptyByArtist.isEmpty());
    }

    @Test
    void deleteById() {
        AlbumEntity albumEntity = createAlbum(artistEntity);

        albumRepository.insert(albumEntity);
        int id = albumEntity.getId();
        assertNotNull(albumRepository.findById(id));

        albumRepository.deleteById(id);
        assertNull(albumRepository.findById(id));
    }

    @Test
    void delete() {
        AlbumEntity albumEntity = createAlbum(artistEntity);

        albumRepository.insert(albumEntity);
        Integer id = albumEntity.getId();
        assertNotNull(albumRepository.findById(id));

        albumRepository.delete(albumEntity);
        assertNull(albumRepository.findById(id));
    }

    @Test
    void update() {
        AlbumEntity albumEntity = createAlbum(artistEntity);
        albumRepository.insert(albumEntity);

        AlbumEntity fetchedAlbumEntity = albumRepository.findById(albumEntity.getId());
        assertEquals(albumEntity, fetchedAlbumEntity);

        fetchedAlbumEntity.setTitle("some title");
        fetchedAlbumEntity.setYear("2004");

        albumRepository.update(fetchedAlbumEntity);

        AlbumEntity updatedAlbumEntity = albumRepository.findById(albumEntity.getId());
        assertEquals(fetchedAlbumEntity, updatedAlbumEntity);

    }

    @Test
    void insert() {
        AlbumEntity albumEntity = createAlbum(artistEntity);
        albumRepository.insert(albumEntity);

        assertEquals(albumEntity, albumRepository.findById(albumEntity.getId()));
    }

    @Test
    void findByCriteria() {
        AlbumEntity albumEntity = createAlbum(artistEntity);
        albumRepository.insert(albumEntity);

        flush();

        List<AlbumEntity> byFullCriteria = albumRepository.findByCriteria(albumEntity);
        assertNotNull(byFullCriteria);
        assertEquals(1, byFullCriteria.size());
        assertEquals(albumEntity, byFullCriteria.get(0));

        AlbumEntity titleAlbumEntity = new AlbumEntity();
        titleAlbumEntity.setTitle(albumEntity.getTitle());

        List<AlbumEntity> byTitle = albumRepository.findByCriteria(titleAlbumEntity);
        assertNotNull(byTitle);
        assertEquals(1, byTitle.size());
        assertEquals(albumEntity, byTitle.get(0));
        assertNotNull(byTitle.get(0).getArtist());

        AlbumEntity yearAlbumEntity = new AlbumEntity();
        yearAlbumEntity.setYear(albumEntity.getYear());

        List<AlbumEntity> byYear = albumRepository.findByCriteria(yearAlbumEntity);
        assertNotNull(byYear);
        assertEquals(1, byYear.size());
        assertEquals(albumEntity, byYear.get(0));
        assertNotNull(byTitle.get(0).getArtist());
    }

    @Test
    void findRecordsByArtist() {
        List<AlbumEntity> emptyRecordsByArtist = albumRepository.findRecordsByArtist(artistEntity.getId());
        assertNotNull(emptyRecordsByArtist);
        assertTrue(emptyRecordsByArtist.isEmpty());

        AlbumEntity albumEntity = createAlbum(artistEntity);
        albumRepository.insert(albumEntity);

        flush();

        List<AlbumEntity> recordsByArtist = albumRepository.findRecordsByArtist(artistEntity.getId());
        assertNotNull(recordsByArtist);
        assertEquals(1, recordsByArtist.size());

        assertEquals(albumEntity, recordsByArtist.get(0));
    }

    @TestConfiguration
    static class LocalTestConfiguration {
        @Bean
        public ArtistRepository artistRepository(SessionFactory sessionFactory) {
            return new ArtistRepository(sessionFactory);
        }

        @Bean
        public AlbumRepository albumRepository(SessionFactory sessionFactory) {
            return new AlbumRepository(sessionFactory);
        }
    }
}