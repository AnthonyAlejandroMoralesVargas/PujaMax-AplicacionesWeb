package model.jpa;

import jakarta.persistence.*;
import model.entities.Bid;
import model.entities.Product;

import java.util.ArrayList;
import java.util.List;

public class BidJPA {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("BidMax");

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public List<Product> findProductsByLotId(int idLot) {
        List<Product> products = new ArrayList<>();
        String jpql = "SELECT p FROM Product p WHERE p.lot.idLot = :idLot";

        try (EntityManager em = getEntityManager()) {
            Query query = em.createQuery(jpql);
            query.setParameter("idLot", idLot);
            products = query.getResultList();
        } catch (Exception e) {
            System.err.println("Couldn't find products by lot ID: " + e.getMessage());
        }
        return products;
    }
    


    public boolean updateBid(Bid bid) {
        boolean result = false;
        try (EntityManager em = getEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.merge(bid);
            transaction.commit();

            result = true;
        } catch (Exception e) {
            System.out.println("Couldn't update bid: " + e.getMessage());
        }
        return result;
    }

    public boolean removeBid(int idBid) {
        boolean result = false;
        try (EntityManager em = getEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            Bid bid = em.find(Bid.class, idBid);
            if (bid != null) {
                em.remove(bid);
                transaction.commit();
                result = true;
            }
        } catch (Exception e) {
            System.out.println("Couldn't remove bid: " + e.getMessage());
        }
        return result;
    }
}
