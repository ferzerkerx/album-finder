package com.ferzerkerx.albumfinder.dao;

import java.util.List;
import com.ferzerkerx.albumfinder.model.Album;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;


@Repository
public class AlbumDao extends BaseDao<Album> {

    public AlbumDao() {
        super(Album.class);
    }

    public void deleteRecordsByArtistId(int artistId) {
        createQuery("DELETE FROM Album a WHERE a.artist.id = :id")
        .setParameter("id", artistId)
        .executeUpdate();
    }


    public List<Album> findRecordsByArtist(int artistId) {
        Query query = createQuery("SELECT a FROM Album a WHERE a.artist.id = :id").setParameter("id", artistId);
        return listAndCast(query);
    }


    public List<Album> findByCriteria(Album example) {
        Criteria criteria = createCriteria().setMaxResults(MAX_RESULTS).setFetchMode("artist", FetchMode.JOIN);

        if (StringUtils.isNotEmpty(example.getTitle())) {
            criteria.add(Restrictions.ilike("title", "%" + example.getTitle() + "%"));
        }

        if (StringUtils.isNotEmpty(example.getYear())) {
            criteria.add(Restrictions.ilike("year", example.getYear()));
        }
        return listAndCast(criteria);
    }
}
