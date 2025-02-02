package model.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import model.entities.Auctioneer;

public class AuctioneerDAO extends GenericDAO<Auctioneer> {
    public AuctioneerDAO() {
        super(Auctioneer.class);
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