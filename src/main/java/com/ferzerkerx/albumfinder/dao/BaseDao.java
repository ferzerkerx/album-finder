package com.ferzerkerx.albumfinder.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class BaseDao<T> {

    static final int MAX_RESULTS = 200;

    private final Class<T> clazz;

    BaseDao(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public T insert(T type) {
        getCurrentSession().save(type);
        return type;
    }

    public T update(T type) {
        getCurrentSession().update(type);
        return type;
    }

    public void deleteById(Integer id) {
        T byId = findById(id);
        delete(byId);
    }

    public T delete(T type) {
        getCurrentSession().delete(type);
        return type;
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

    Criteria createCriteria() {
        return getCurrentSession().createCriteria(clazz);
    }

    @SuppressWarnings("unchecked")
    static <T> List<T> listAndCast(Query query) {
        return query.list();
    }

    @SuppressWarnings("unchecked")
    static <T> List<T> listAndCast(Criteria criteria) {
        return criteria.list();
    }

}
