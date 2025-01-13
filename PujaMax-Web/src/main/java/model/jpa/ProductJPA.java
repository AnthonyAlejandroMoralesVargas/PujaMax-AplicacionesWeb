package model.jpa;

import jakarta.persistence.*;
import model.entities.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductJPA {
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

    public boolean createProduct(Product product) {
        boolean result = false;
        try (EntityManager em = getEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(product);
            transaction.commit();

            result = true;
        } catch (Exception e) {
            System.out.println("Couldn't create product: " + e.getMessage());
        }
        return result;
    }

    public Product findProductById(int idProduct) {
        Product product = null;
        try (EntityManager em = getEntityManager()) {
            product = em.find(Product.class, idProduct);
        } catch (Exception e) {
            System.out.println("Couldn't find product by ID: " + e.getMessage());
        }
        return product;
    }

    public boolean updateProduct(Product product) {
        boolean result = false;
        try (EntityManager em = getEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.merge(product);
            transaction.commit();

            result = true;
        } catch (Exception e) {
            System.out.println("Couldn't update product: " + e.getMessage());
        }
        return result;
    }

    public boolean removeProduct(int idProduct) {
        boolean result = false;
        try (EntityManager em = getEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            Product product = em.find(Product.class, idProduct);
            if (product != null) {
                em.remove(product);
                transaction.commit();
                result = true;
            }
        } catch (Exception e) {
            System.out.println("Couldn't remove product: " + e.getMessage());
        }
        return result;
    }
}
