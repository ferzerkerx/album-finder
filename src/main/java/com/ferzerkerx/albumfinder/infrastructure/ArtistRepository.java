package com.ferzerkerx.albumfinder.infrastructure;

import com.ferzerkerx.albumfinder.infrastructure.entity.ArtistEntity;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArtistRepository extends BaseRepository<ArtistEntity> {

    public ArtistRepository(SessionFactory sessionFactory) {
        super(ArtistEntity.class, sessionFactory);
    }

    public List<ArtistEntity> findAllArtists() {
        Query<ArtistEntity> query = createTypedQuery("SELECT a FROM Artist a");
        return query.getResultList();
    }

    public List<ArtistEntity> findArtistsByName(String name) {
        Query<ArtistEntity> query = createTypedQuery("SELECT a FROM Artist a WHERE lower(a.name) LIKE lower(:name)");
        query.setMaxResults(MAX_RESULTS);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }
}
