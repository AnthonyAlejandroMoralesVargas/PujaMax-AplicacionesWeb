package model.dao;

import jakarta.persistence.*;
import model.entities.Lot;
import model.entities.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO extends GenericDAO<Product> {


    public ProductDAO() {
        super(Product.class);
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

    @Override
    public boolean create(Product product) {
        boolean result = false;
        EntityManager em = getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(product);

            // Actualizar la cantidad de productos en el lote
            Lot lot = product.getLot();
            lot.setQuantityProducts(lot.getQuantityProducts() + 1);
            em.merge(lot);

            transaction.commit();
            result = true;
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Couldn't create product: " + e.getMessage());
        } finally {
            em.close();
        }
        return result;
    }

    @Override
    public boolean remove(Object idProduct) {
        boolean result = false;
        EntityManager em = getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Product product = em.find(Product.class, idProduct);
            if (product != null) {
                Lot lot = product.getLot();
                lot.setQuantityProducts(Math.max(0, lot.getQuantityProducts() - 1));
                em.merge(lot);
                em.remove(product);
            }

            transaction.commit();
            result = true;
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Couldn't remove product: " + e.getMessage());
        } finally {
            em.close();
        }
        return result;
    }
}
