package com.ferzerkerx.albumfinder.repository;

import com.ferzerkerx.albumfinder.model.Artist;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArtistRepository extends BaseRepository<Artist> {

    public ArtistRepository() {
        super(Artist.class);
    }

    public List<Artist> findAllArtists() {
        Query<Artist> query = createTypedQuery("SELECT a FROM Artist a");
        return query.getResultList();
    }

    public List<Artist> findMatchedArtistsByName(String name) {
        Query<Artist> query = createTypedQuery("SELECT a FROM Artist a WHERE lower(a.name) LIKE lower(:name)");
        query.setMaxResults(MAX_RESULTS);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }
}
