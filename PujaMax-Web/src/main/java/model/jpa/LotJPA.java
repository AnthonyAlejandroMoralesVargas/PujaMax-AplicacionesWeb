package model.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.entities.Lot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LotJPA {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("BidMax");

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Lot> findLotsByIdAuctioneer(int idAuctioneer) {
        EntityManager em = getEntityManager();
        List<Lot> lots = new ArrayList<>();
        Date now = new Date();

        try {
            // Consulta JPA para obtener los lotes
            List<Lot> resultList = em.createQuery(
                            "SELECT l FROM Lot l WHERE l.auctioneer.id = :idAuctioneer", Lot.class
                    ).setParameter("idAuctioneer", idAuctioneer)
                    .getResultList();

            for (Lot lot : resultList) {
                // Verificar si debería estar "ACTIVE" o "INACTIVE"
                boolean shouldBeActive = !now.before(lot.getDateOpening()) && !now.after(lot.getDateClosing());

                // Si hay discrepancia entre shouldBeActive y lot.state, actualizar en la BD
                em.getTransaction().begin();
                if (shouldBeActive && "INACTIVE".equals(lot.getState())) {
                    lot.setState("ACTIVE");
                    em.merge(lot); // Actualizar estado
                } else if (!shouldBeActive && "ACTIVE".equals(lot.getState())) {
                    lot.setState("INACTIVE");
                    em.merge(lot); // Actualizar estado
                }
                em.getTransaction().commit();

                lots.add(lot);
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Error when finding lots by ID Auctioneer");
        } finally {
            em.close();
        }
        return lots;
    }

    public boolean createLot(Lot lot) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(lot);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Error when creating lot");
            return false;
        } finally {
            em.close();
        }
    }

    public Lot findLotById(int idLot) {
        try (EntityManager em = getEntityManager()) {
            Lot lot = em.find(Lot.class, idLot);

            if (lot != null) {
                Date now = new Date();
                boolean shouldBeActive = !now.before(lot.getDateOpening()) && !now.after(lot.getDateClosing());

                em.getTransaction().begin();
                if (shouldBeActive && "INACTIVE".equals(lot.getState())) {
                    lot.setState("ACTIVE");
                    em.merge(lot);
                } else if (!shouldBeActive && "ACTIVE".equals(lot.getState())) {
                    lot.setState("INACTIVE");
                    em.merge(lot);
                }
                em.getTransaction().commit();
            }

            return lot;
        } catch (Exception e) {
            System.out.println("Error when finding lot by ID");
            return null;
        }
    }

    public boolean updateLot(Lot lot) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Lot existingLot = em.find(Lot.class, lot.getIdLot());
            if (existingLot != null) {
                copyLotProperties(existingLot, lot);
                em.merge(existingLot);
                em.getTransaction().commit();
                return true;
            } else {
                em.getTransaction().rollback();
                return false;
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Error when updating lot");
            return false;
        } finally {
            em.close();
        }
    }

    public boolean removeLot(int idLot) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Lot lot = em.find(Lot.class, idLot);
            if (lot != null) {
                em.remove(lot);
                em.getTransaction().commit();
                return true;
            } else {
                em.getTransaction().rollback();
                return false;
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Error when removing lot");
            return false;
        } finally {
            em.close();
        }
    }

    private void copyLotProperties(Lot target, Lot source) {
        target.setTitle(source.getTitle());
        target.setQuantityProducts(source.getQuantityProducts());
        target.setDateOpening(source.getDateOpening());
        target.setDateClosing(source.getDateClosing());
        target.setCity(source.getCity());
        target.setState(source.getState());
        target.setAddress(source.getAddress());
        target.setAuctioneer(source.getAuctioneer());
    }
}
