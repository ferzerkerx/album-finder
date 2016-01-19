package com.ferzerkerx.album_finder.dao;

import java.util.Collection;
import java.util.List;
import com.ferzerkerx.album_finder.model.Artist;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository
public class ArtistDao extends BaseDao<Artist> {

    public ArtistDao() {
        super(Artist.class);
    }


    public Collection<Artist> findAllArtists() {
        Query query = createQuery("SELECT a FROM Artist a");
        return listAndCast(query);
    }


    public List<Artist> findMatchedArtistsByName(String name) {
        Query query = createQuery("SELECT a FROM Artist a WHERE a.name LIKE :name");
        query.setParameter("name", "%" + name + "%");
        return listAndCast(query);
    }
}
