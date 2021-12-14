package com.ferzerkerx.albumfinder.infrastructure;

import com.ferzerkerx.albumfinder.DbIntegrationTest;
import com.ferzerkerx.albumfinder.infrastructure.entity.ArtistEntity;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.List;

import static com.ferzerkerx.albumfinder.Fixtures.createArtist;
import static org.junit.jupiter.api.Assertions.*;

class ArtistEntityRepositoryTest extends DbIntegrationTest {

    @Autowired
    private ArtistRepository artistRepository;

    @Test
    void findMatchedArtistsByName() {
        ArtistEntity artistEntity = createArtist();
        artistRepository.insert(artistEntity);

        flush();

        List<ArtistEntity> matchedArtistsByName = artistRepository.findArtistsByName(artistEntity.getName());
        assertNotNull(matchedArtistsByName);
        assertEquals(1, matchedArtistsByName.size());
        assertEquals(artistEntity, matchedArtistsByName.get(0));
    }

    @Test
    void findAllArtists() {
        List<ArtistEntity> emptyArtistEntities = artistRepository.findAllArtists();
        assertNotNull(emptyArtistEntities);
        assertTrue(emptyArtistEntities.isEmpty());

        artistRepository.insert(createArtist());
        artistRepository.insert(createArtist());

        flush();

        List<ArtistEntity> artistEntities = artistRepository.findAllArtists();
        assertNotNull(artistEntities);
        assertEquals(2, artistEntities.size());
    }


    @Test
    void deleteById() {
        ArtistEntity artistEntity = createArtist();
        artistRepository.insert(artistEntity);

        ArtistEntity storedArtistEntity = artistRepository.findById(artistEntity.getId());
        assertEquals(artistEntity, storedArtistEntity);

        artistRepository.deleteById(storedArtistEntity.getId());
        assertNull(artistRepository.findById(storedArtistEntity.getId()));
    }

    @Test
    void delete() {
        ArtistEntity artistEntity = createArtist();
        artistRepository.insert(artistEntity);

        ArtistEntity storedArtistEntity = artistRepository.findById(artistEntity.getId());
        assertEquals(artistEntity, storedArtistEntity);

        artistRepository.delete(storedArtistEntity);
        assertNull(artistRepository.findById(storedArtistEntity.getId()));
    }

    @Test
    void update() {
        ArtistEntity artistEntity = createArtist();
        artistRepository.insert(artistEntity);

        ArtistEntity storedArtistEntity = artistRepository.findById(artistEntity.getId());
        assertEquals(artistEntity, storedArtistEntity);

        storedArtistEntity.setName("new name");
        artistRepository.update(artistEntity);

        ArtistEntity updatedArtistEntity = artistRepository.findById(storedArtistEntity.getId());
        assertEquals(storedArtistEntity, updatedArtistEntity);
    }

    @Test
    void insert() {
        ArtistEntity artistEntity = createArtist();
        artistRepository.insert(artistEntity);

        ArtistEntity storedArtistEntity = artistRepository.findById(artistEntity.getId());
        assertEquals(artistEntity, storedArtistEntity);
    }

    @TestConfiguration
    static class LocalTestConfiguration {
        @Bean
        public ArtistRepository artistRepository(SessionFactory sessionFactory) {
            return new ArtistRepository(sessionFactory);
        }
    }
}