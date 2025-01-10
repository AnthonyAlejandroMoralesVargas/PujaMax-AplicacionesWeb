package model.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import model.entities.Auctioneer;

public class AuctioneerJPA {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("BidMax");

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean create(Auctioneer auctioneer) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(auctioneer);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("User could not be created");
            return false;
        } finally {
            em.close();
        }
    }

    public Auctioneer findByDni(String dni) {
        try (EntityManager em = getEntityManager()) {
            return em.createQuery("SELECT a FROM Auctioneer a WHERE a.dni = :dni", Auctioneer.class)
                    .setParameter("dni", dni)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            System.out.println("An error occurred while trying to find the auctioneer by DNI");
            return null;
        }
    }
}