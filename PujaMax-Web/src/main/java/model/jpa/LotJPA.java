package model.jpa;

import jakarta.persistence.*;
import model.entities.Bid;
import model.entities.Lot;
import model.entities.Product;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class LotJPA {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("BidMax");

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Lot> findLotsByIdAuctioneer(int idAuctioneer) {
        List<Lot> lots = new ArrayList<>();
        Date now = new Date();

        String jpql = "SELECT l FROM Lot l WHERE l.auctioneer.id = :idAuctioneer";

        try (EntityManager em = getEntityManager()) {
            Query query = em.createQuery(jpql);
            query.setParameter("idAuctioneer", idAuctioneer);

            lots = query.getResultList();

            for (Lot lot : lots) {
                boolean shouldBeActive = !now.before(lot.getDateOpening()) && !now.after(lot.getDateClosing());

                if (shouldBeActive) {
                    if (!"ACTIVE".equals(lot.getState())) {
                        lot.setState("ACTIVE");
                        updateLotState(em, lot);
                    }
                } else {
                    if (!"INACTIVE".equals(lot.getState())) {
                        lot.setState("INACTIVE");
                        updateLotState(em, lot);
                        List<Product> products = new ProductJPA().findProductsByLotId(lot.getIdLot());
                        for (Product product : products) {
                            List<Bid> bids = new BidJPA().findBidByProductId(product.getIdProduct());
                            Bid winningBid = bids.stream()
                                                 .max(Comparator.comparing(Bid::getAmount))
                                                 .orElse(null);

                            for (Bid bid : bids) {
                                if (bid.equals(winningBid)) {
                                    bid.setState(Bid.BidState.WON);
                                } else {
                                    bid.setState(Bid.BidState.LOST);
                                }
                                new BidJPA().updateBid(bid); // Persistir cambios en las pujas
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Couldn't find lots by auctioneer ID: " + e.getMessage());
        }
        return lots;
    }

    private void updateLotState(EntityManager em, Lot lot) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(lot);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public boolean createLot(Lot lot) {
        boolean result = false;
        try (EntityManager em = getEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(lot);
            transaction.commit();

            result = true;
        } catch (Exception e) {
            System.out.println("Couldn't create lot: " + e.getMessage());
        }
        return result;
    }

    public Lot findLotById(int idLot) {
        Lot lot = null;
        try (EntityManager em = getEntityManager()) {
            lot = em.find(Lot.class, idLot);

            if (lot != null) {
                Date now = new Date();
                boolean shouldBeActive = !now.before(lot.getDateOpening()) && !now.after(lot.getDateClosing());

                if (shouldBeActive) {
                    if (!"ACTIVE".equals(lot.getState())) {
                        lot.setState("ACTIVE");
                        updateLotState(em, lot);
                    }
                } else {
                    if (!"INACTIVE".equals(lot.getState())) {
                        lot.setState("INACTIVE");
                        updateLotState(em, lot);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Couldn't find lot by ID: " + e.getMessage());
        }
        return lot;
    }

    public boolean updateLot(Lot lot) {
        boolean result = false;
        try (EntityManager em = getEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.merge(lot);
            transaction.commit();

            result = true;
        } catch (Exception e) {
            System.out.println("Couldn't update lot: " + e.getMessage());
        }
        return result;
    }

    public boolean removeLot(int idLot) {
        boolean result = false;
        try (EntityManager em = getEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            Lot lot = em.find(Lot.class, idLot);
            if (lot != null) {
                em.remove(lot);
                transaction.commit();
                result = true;
            }
        } catch (Exception e) {
            System.out.println("Couldn't remove lot: " + e.getMessage());
        }
        return result;
    }
    
    public List<Lot> findLotsByState(String state) {
        List<Lot> lots = new ArrayList<>();
        Date now = new Date();

        String jpql = "SELECT l FROM Lot l WHERE l.state = :state";

        try (EntityManager em = getEntityManager()) {
            Query query = em.createQuery(jpql);
            query.setParameter("state", state);
            lots = query.getResultList();

            for (Lot lot : lots) {
                boolean shouldBeActive = !now.before(lot.getDateOpening()) && !now.after(lot.getDateClosing());

                if ("ACTIVE".equals(state) && !shouldBeActive) {
                    lot.setState("INACTIVE");
                    updateLotState(em, lot);
                }
            }

            if ("ACTIVE".equals(state)) {
                lots.removeIf(lot -> "INACTIVE".equals(lot.getState()));
            }
        } catch (Exception e) {
            System.err.println("Couldn't find lots by state: " + e.getMessage());
        }
        return lots;
    }
}

