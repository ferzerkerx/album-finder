package com.ferzerkerx.album_finder.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.IdentifierLoadAccess;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseDao<T> {

    protected static final int MAX_RESULTS = 200;

    private final Class<T> clazz;

    public BaseDao(Class<T> clazz) {
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

    @SuppressWarnings("unchecked")
    public T findById(Integer id) {
        return (T) getCurrentSession().get(clazz, id);
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
