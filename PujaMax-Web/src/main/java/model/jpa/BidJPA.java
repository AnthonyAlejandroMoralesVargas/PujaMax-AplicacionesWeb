package model.jpa;

import jakarta.persistence.*;
import model.entities.Bid;
import model.entities.Lot;
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
    /*
    public boolean updateBid(Bid bid) {
        boolean result = false;
        EntityManager em = null;
        try {
            em = getEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            // Verificar si es nuevo o existente
            if (bid.getIdBid() == 0) { // Si no tiene ID, es nuevo
                em.persist(bid);
                System.out.println("Persisting new Bid: " + bid);
            } else {
                em.merge(bid);
                System.out.println("Merging existing Bid: " + bid);
            }

            transaction.commit();
            result = true;
            System.out.println("Bid saved successfully");
        } catch (Exception e) {
            System.err.println("Couldn't update bid: " + e.getMessage());
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return result;
    }
*/

    public List<Bid> findBidsByProductId(int productId) {
        List<Bid> bids = new ArrayList<>();
        String jpql = "SELECT b FROM Bid b WHERE b.product.idProduct = :productId";

        EntityManager em = null;
        try {
            em = getEntityManager();
            Query query = em.createQuery(jpql, Bid.class);
            query.setParameter("productId", productId);
            bids = query.getResultList();
        } catch (Exception e) {
            System.err.println("Couldn't find bids for product ID " + productId + ": " + e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return bids;
    }
    
    public boolean createBid(Bid bid) {
        boolean result = false;
        try (EntityManager em = getEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(bid);
            transaction.commit();

            result = true;
        } catch (Exception e) {
            System.out.println("Couldn't create bid: " + e.getMessage());
        }
        return result;
    }

    
    public boolean saveBid(Bid bid) {
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


}
