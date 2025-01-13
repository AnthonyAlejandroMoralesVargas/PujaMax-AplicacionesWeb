package model.jpa;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.entities.Address;

public class AddressJPA {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("BidMax");

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Address> findAddressesByIdAuctioneer(int idAuctioneer) {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT a FROM Address a WHERE a.auctioneer.id = :idAuctioneer";
            return em.createQuery(jpql, Address.class)
                    .setParameter("idAuctioneer", idAuctioneer)
                    .getResultList();
        } catch (Exception e) {
            System.out.println("Error when searching for auctioneer addresses");
            return List.of();
        }
    }

    public boolean createAddress(Address address) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(address);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Error when creating address");
            return false;
        } finally {
            em.close();
        }
    }

    public boolean updateAddress(Address address) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Address existingAddress = em.find(Address.class, address.getIdAddress());
            if (existingAddress != null) {
                copyAddressProperties(existingAddress, address);
                em.merge(existingAddress);
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
            System.out.println("Error when updating address");
            return false;
        } finally {
            em.close();
        }
    }

    public Address findAddressById(int idAddress) {
        try (EntityManager em = getEntityManager()) {
            return em.find(Address.class, idAddress);
        } catch (Exception e) {
            System.out.println("Error when searching for address");
            return null;
        }
    }

    public boolean removeAddress(int idAddress) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Address address = em.find(Address.class, idAddress);
            if (address != null) {
                em.remove(address);
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
            System.out.println("Error when removing address");
            return false;
        } finally {
            em.close();
        }
    }

    private void copyAddressProperties(Address target, Address source) {
        target.setName(source.getName());
        target.setProvince(source.getProvince());
        target.setCity(source.getCity());
        target.setMainStreet(source.getMainStreet());
        target.setSecondaryStreet(source.getSecondaryStreet());
        target.setPostcode(source.getPostcode());
        target.setHouseNumber(source.getHouseNumber());
        target.setCompany(source.getCompany());
    }
}
