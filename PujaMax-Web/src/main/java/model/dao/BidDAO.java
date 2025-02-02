package model.dao;

import jakarta.persistence.*;
import model.entities.Bid;
import model.entities.Product;

import java.util.ArrayList;
import java.util.List;

public class BidDAO extends GenericDAO<Bid>{

    public BidDAO() {
        super(Bid.class);
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

    @Override
    public boolean create(Bid bid) {
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
