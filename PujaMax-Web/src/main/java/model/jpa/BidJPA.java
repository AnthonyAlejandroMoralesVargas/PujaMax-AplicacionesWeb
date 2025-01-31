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
            Product product = bid.getProduct(); 

            if (bid.getAmount() <= product.getPriceCurrent()) {
                transaction.rollback();
                return false;
            } else {
                List<Bid> previousBids = em.createQuery(
                        "SELECT b FROM Bid b WHERE b.product.idProduct = :productId", Bid.class)
                        .setParameter("productId", product.getIdProduct())
                        .getResultList();
                for (Bid previousBid : previousBids) {
                    previousBid.setState(Bid.BidState.SURPASSED);
                    em.merge(previousBid);
                }
                bid.setState(Bid.BidState.TOP);
                
                em.persist(bid);
                product.setPriceCurrent(bid.getAmount());
                em.merge(product);
                transaction.commit();
                result = true;
            }
        } catch (Exception e) {
            System.out.println("Couldn't create bid: " + e.getMessage());
        }
        return result;
    }


    public boolean updateBid(Bid bid) {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(bid);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Couldn't update bid: " + e.getMessage());
        } finally {
            em.close();
        }
		return false;
    }


    public List<Bid> getBidsByUserId(int userId) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT b FROM Bid b WHERE b.bidder.id = :userId", Bid.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } catch (Exception e) {
            System.out.println("Error fetching bids by user ID: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

}
