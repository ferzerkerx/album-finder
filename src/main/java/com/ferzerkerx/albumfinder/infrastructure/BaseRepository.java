package com.ferzerkerx.albumfinder.infrastructure;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

@RequiredArgsConstructor
public abstract class BaseRepository<T> {

    static final int MAX_RESULTS = 200;

    private final Class<T> clazz;

    public Class<T> getClazz() {
        return clazz;
    }

    private final SessionFactory sessionFactory;

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public void insert(@NonNull T type) {
        getCurrentSession().save(type);
    }

    public T update(@NonNull T type) {
        getCurrentSession().update(type);
        return type;
    }

    public void deleteById(@NonNull Integer id) {
        T byId = findById(id);
        delete(byId);
    }

    public void delete(@NonNull T type) {
        getCurrentSession().delete(type);
    }

    public T findById(@NonNull Integer id) {
        return getCurrentSession().get(clazz, id);
    }

    Query createQuery(@NonNull String query) {
        return getCurrentSession().createQuery(query);
    }

    Query<T> createQuery(@NonNull CriteriaQuery<T> criteriaQuery) {
        return getCurrentSession().createQuery(criteriaQuery);
    }

    Query<T> createTypedQuery(@NonNull String query) {
        return getCurrentSession().createQuery(query, clazz);
    }

    CriteriaBuilder getCriteriaBuilder() {
        return getCurrentSession().getCriteriaBuilder();
    }

    CriteriaQuery<T> createCriteriaQuery() {
        return getCriteriaBuilder().createQuery(clazz);
    }
}
