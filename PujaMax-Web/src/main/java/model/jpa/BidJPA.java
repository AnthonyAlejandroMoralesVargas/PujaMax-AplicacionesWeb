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

    public List<Bid> findBidByProductId(int productId) {
        List<Bid> bids = new ArrayList<>();
        String jpql = "SELECT b FROM Bid b WHERE b.product.idProduct = :productId";

        try (EntityManager em = getEntityManager()) {
            Query query = em.createQuery(jpql, Bid.class);
            query.setParameter("productId", productId);
            bids = query.getResultList();
        } catch (Exception e) {
            System.err.println("Couldn't find bids for product ID " + productId + ": " + e.getMessage());
        }
        return bids;
    }

    public List<Bid> findActiveBidsByProductId(int productId) {
        List<Bid> bids = new ArrayList<>();
        String jpql = "SELECT b FROM Bid b WHERE b.product.idProduct = :productId AND b.state = :state";

        try (EntityManager em = getEntityManager()) {
            Query query = em.createQuery(jpql, Bid.class);
            query.setParameter("productId", productId);
            query.setParameter("state", Bid.BidState.ACTIVE);
            bids = query.getResultList();
        } catch (Exception e) {
            System.err.println("Couldn't find active bids for product ID " + productId + ": " + e.getMessage());
        }
        return bids;
    }

    
    public List<Bid> findBidsByUserId(int userId) {
        List<Bid> bids = new ArrayList<>();
        String jpql = "SELECT b FROM Bid b WHERE b.user.id = :userId"; // Esto ya funciona correctamente

        try (EntityManager em = getEntityManager()) {
            Query query = em.createQuery(jpql, Bid.class);
            query.setParameter("userId", userId);
            bids = query.getResultList();
        } catch (Exception e) {
            System.err.println("Couldn't find bids for user ID " + userId + ": " + e.getMessage());
        }
        return bids;
    }


    public Bid findBidById(int idBid) {
        Bid bid = null;
        try (EntityManager em = getEntityManager()) {
            bid = em.find(Bid.class, idBid);
        } catch (Exception e) {
            System.out.println("Couldn't find bid by ID: " + e.getMessage());
        }
        return bid;
    }
    
    public List<Bid> getBids(String dni) {
        String jpql = "SELECT b FROM Bid b JOIN FETCH b.product WHERE b.bidder.dni = :dni";
        List<Bid> bids = new ArrayList<>();
        try (EntityManager em = getEntityManager()) {
            TypedQuery<Bid> query = em.createQuery(jpql, Bid.class);
            query.setParameter("dni", dni);
            bids = query.getResultList();
        } catch (Exception e) {
            System.err.println("Couldn't fetch bids for bidder DNI: " + e.getMessage());
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

    public void expireActiveBidsForProduct(int productId) {
        String jpql = "UPDATE Bid b SET b.state = :expired WHERE b.product.idProduct = :productId AND b.state = :active";

        try (EntityManager em = getEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            Query query = em.createQuery(jpql);
            query.setParameter("expired", Bid.BidState.LOST);
            query.setParameter("productId", productId);
            query.setParameter("active", Bid.BidState.ACTIVE);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Couldn't expire active bids: " + e.getMessage());
        }
    }
}
