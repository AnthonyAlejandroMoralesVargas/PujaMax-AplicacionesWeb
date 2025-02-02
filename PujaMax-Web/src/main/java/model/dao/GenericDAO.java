package model.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;
import jakarta.persistence.*;

public abstract class GenericDAO<T> {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("BidMax");
    private final Class<T> entityClass;

    protected GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean create(T entity) {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(entity);
            transaction.commit();
            return true;
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Couldn't create entity: " + e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }

    public T findById(Object id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(entityClass, id);
        } catch (Exception e) {
            System.out.println("Couldn't find entity: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public boolean update(T entity) {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(entity);
            transaction.commit();
            return true;
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Couldn't update entity: " + e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }

    public boolean remove(Object id) {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            T entity = em.find(entityClass, id);
            if (entity != null) {
                em.remove(entity);
                transaction.commit();
                return true;
            }
            return false;
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Couldn't remove entity: " + e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }

    public List<T> findAll() {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e";
            Query query = em.createQuery(jpql, entityClass);
            return query.getResultList();
        } catch (Exception e) {
            System.out.println("Couldn't retrieve entities: " + e.getMessage());
            return List.of();
        } finally {
            em.close();
        }
    }
}
