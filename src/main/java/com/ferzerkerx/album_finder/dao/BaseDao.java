package com.ferzerkerx.album_finder.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.IdentifierLoadAccess;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseDao<T> {

    private final Class<T> clazz;

    public BaseDao(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
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
        String hql = "DELETE " + clazz.getName() + " WHERE id = :id";
        Query q = getCurrentSession().createQuery(hql).setParameter("id", id);
        q.executeUpdate();
    }


    public T delete(T type) {
        getCurrentSession().delete(type);
        return type;
    }


    public List<T> findByCriteria(T criteria) {
        throw new UnsupportedOperationException("This is not implemented yet");
    }


    public T findById(Integer id) {
        IdentifierLoadAccess identifierLoadAccess = getCurrentSession().byId(clazz);
        return (T) identifierLoadAccess.getReference(id);
    }


    protected Query createQuery(String query) {
        return getCurrentSession().createQuery(query);
    }

    protected Criteria createCriteria() {
        return getCurrentSession().createCriteria(clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> listAndCast(Query query) {
        return query.list();
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> listAndCast(Criteria criteria) {
        return criteria.list();
    }

}
