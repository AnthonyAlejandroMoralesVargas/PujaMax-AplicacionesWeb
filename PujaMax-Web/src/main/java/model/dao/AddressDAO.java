package model.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.entities.Address;

public class AddressDAO extends GenericDAO<Address> {
    public AddressDAO() {
        super(Address.class);
    }

    public List<Address> findAddressesByIdAuctioneer(int idAuctioneer) {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT a FROM Address a WHERE a.auctioneer.id = :idAuctioneer";
            return em.createQuery(jpql, Address.class)
                    .setParameter("idAuctioneer", idAuctioneer)
                    .getResultList();
        } catch (Exception e) {
            return List.of();
        }
    }
}
