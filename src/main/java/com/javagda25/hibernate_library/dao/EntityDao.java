package com.javagda25.hibernate_library.dao;

import com.javagda25.hibernate_library.utils.HibernateUtil;
import com.javagda25.hibernate_library.model.IBaseEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EntityDao {
    public <T extends IBaseEntity> void saveOrUpdate (T entity) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            session.saveOrUpdate(entity);

            transaction.commit();
        } catch (HibernateException he) {
            if (transaction!=null) {
                transaction.rollback();
            }
        }
    }

    public <T extends IBaseEntity> List<T> getall(Class<T> tClass) {
        List<T> tList = new ArrayList<>();
        SessionFactory factory = HibernateUtil.getSessionFactory();

        try (Session session = factory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = cb.createQuery(tClass);
            Root<T> rootTable = criteriaQuery.from(tClass);
            criteriaQuery.select(rootTable);

            tList.addAll(session.createQuery(criteriaQuery).list());
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return tList;
    }

    public <T extends IBaseEntity> Optional<T> getById (Class<T> tClass, Long id) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            T entity = session.get(tClass, id);

            return Optional.ofNullable(entity);
        }
    }

    public <T extends IBaseEntity> void delete(Class<T> classT, Long id) {
        Optional<T> optionalEntity = getById(classT, id);

        if (optionalEntity.isPresent()) {
            delete(optionalEntity.get());
        } else {
            System.out.println("Instance not found");
        }
    }

    private  <T extends IBaseEntity> void delete(T entity) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();

            session.delete(entity);

            transaction.commit();
        }
    }

}
