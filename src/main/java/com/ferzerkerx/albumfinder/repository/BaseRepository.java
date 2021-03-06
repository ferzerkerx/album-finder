package com.ferzerkerx.albumfinder.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public abstract class BaseRepository<T> {

    static final int MAX_RESULTS = 200;

    private final Class<T> clazz;

    BaseRepository(Class<T> clazz) {
        this.clazz = clazz;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public void insert(T type) {
        getCurrentSession().save(type);
    }

    public T update(T type) {
        getCurrentSession().update(type);
        return type;
    }

    public void deleteById(Integer id) {
        T byId = findById(id);
        delete(byId);
    }

    public void delete(T type) {
        getCurrentSession().delete(type);
    }

    public List<T> findByCriteria(T criteria) {
        throw new UnsupportedOperationException("This is not implemented yet");
    }

    public T findById(Integer id) {
        return getCurrentSession().get(clazz, id);
    }

    Query createQuery(String query) {
        return getCurrentSession().createQuery(query);
    }

    Query<T> createQuery(CriteriaQuery<T> criteriaQuery) {
        return getCurrentSession().createQuery(criteriaQuery);
    }

    Query<T> createTypedQuery(String query) {
        return getCurrentSession().createQuery(query, clazz);
    }

    CriteriaBuilder getCriteriaBuilder() {
        return getCurrentSession().getCriteriaBuilder();
    }

    CriteriaQuery<T> createCriteriaQuery() {
        return getCriteriaBuilder().createQuery(clazz);
    }
}
