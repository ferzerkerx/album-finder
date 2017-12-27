package com.ferzerkerx.albumfinder.dao;

import com.ferzerkerx.albumfinder.model.Artist;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArtistDao extends BaseDao<Artist> {

    public ArtistDao() {
        super(Artist.class);
    }

    public List<Artist> findAllArtists() {
        Query query = createQuery("SELECT a FROM Artist a");
        return listAndCast(query);
    }


    public List<Artist> findMatchedArtistsByName(String name) {
        Query query = createQuery("SELECT a FROM Artist a WHERE lower(a.name) LIKE lower(:name)");
        query.setMaxResults(MAX_RESULTS);
        query.setParameter("name", "%" + name + "%");
        return listAndCast(query);
    }
}
