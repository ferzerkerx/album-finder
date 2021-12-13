package com.ferzerkerx.albumfinder.repository;

import com.ferzerkerx.albumfinder.model.Album;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;


@Repository
public class AlbumRepository extends BaseRepository<Album> {

    public AlbumRepository() {
        super(Album.class);
    }

    public void deleteRecordsByArtistId(int artistId) {
        createQuery("DELETE FROM Album a WHERE a.artist.id = :id")
                .setParameter("id", artistId)
                .executeUpdate();
    }

    public List<Album> findRecordsByArtist(int artistId) {
        Query<Album> query = createTypedQuery("SELECT a FROM Album a WHERE a.artist.id = :id")
                .setParameter("id", artistId);
        return query.getResultList();
    }


    @Override
    public List<Album> findByCriteria(Album example) {

        CriteriaBuilder builder = getCriteriaBuilder();

        CriteriaQuery<Album> criteria = createCriteriaQuery();
        Root<Album> root = criteria.from(getClazz());
        root.fetch("artist");
        criteria.select(root);

        List<Predicate> predicates = new LinkedList<>();
        if (StringUtils.isNotEmpty(example.getTitle())) {
            Predicate titlePredicate = builder.like(root.get("title"), "%" + example.getTitle() + "%");
            predicates.add(titlePredicate);
        }

        if (StringUtils.isNotEmpty(example.getYear())) {
            Predicate yearPredicate = builder.equal(root.get("year"), example.getYear());
            predicates.add(yearPredicate);
        }

        if (!CollectionUtils.isEmpty(predicates)) {
            criteria.where(predicates.toArray(new Predicate[0]));
        }

        Query<Album> query = createQuery(criteria);
        query.setMaxResults(MAX_RESULTS);
        return query.getResultList();
    }
}
