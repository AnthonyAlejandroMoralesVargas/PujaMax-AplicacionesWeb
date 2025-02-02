package model.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import model.entities.Bidder;

public class BidderDAO extends GenericDAO<Bidder> {

    public BidderDAO() {
        super(Bidder.class);
    }

    public Bidder findByDni(String dni) {
        try (EntityManager em = getEntityManager()) {
            return em.createQuery("SELECT b FROM Bidder b WHERE b.dni = :dni", Bidder.class)
                    .setParameter("dni", dni)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            System.out.println("An error occurred while trying to find the bidder by DNI");
            return null;
        }
    }
}