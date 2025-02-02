package model.dao;

import jakarta.persistence.*;
import model.entities.Bid;
import model.entities.Receipt;

import java.time.LocalDateTime;
import java.util.List;

public class ReceiptDAO extends GenericDAO<Receipt>{
    public ReceiptDAO() {
        super(Receipt.class);
    }

    public void createPayment(List<String> base64Images, int bidId) {
        if (base64Images == null || base64Images.isEmpty()) {
            throw new IllegalArgumentException("The images list cannot be null or empty.");
        }

        EntityManager entityManager = getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            // Buscar la puja
            Bid bid = entityManager.find(Bid.class, bidId);
            if (bid == null) {
                throw new IllegalArgumentException("Bid not found with ID: " + bidId);
            }

            // Cambiar el estado de la puja a PENDING_APPROVAL
            bid.setState(Bid.BidState.PENDING_APPROVAL);
            entityManager.merge(bid);


            // Crear y guardar el recibo
            Receipt receipt = new Receipt();
            receipt.setBid(bid);
            receipt.setDate(LocalDateTime.now().toString());

            // Persistir el recibo
            entityManager.persist(receipt);

            // Guardar imágenes relacionadas
            for (String base64Image : base64Images) {
                receipt.getImages().add(base64Image);
            }

            // Actualizar el recibo con las imágenes
            entityManager.merge(receipt);
            bid.setReceipt(receipt);
            entityManager.merge(bid);
            transaction.commit();
            System.out.println("Receipt and images successfully saved.");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw e;
        } finally {
            entityManager.close();
        }
    }
    
    public boolean approveReceipt(int idReceipt) {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            // Buscar el recibo por ID
            Receipt receipt = em.find(Receipt.class, idReceipt);
            if (receipt == null) {
                throw new IllegalArgumentException("Receipt not found with ID: " + idReceipt);
            }

            // Obtener la puja asociada
            Bid bid = receipt.getBid();
            if (bid.getState() != Bid.BidState.PENDING_APPROVAL) {
                throw new IllegalStateException("Only bids in PENDING_APPROVAL state can be approved.");
            }

            // Cambiar el estado de la puja a ACCEPT
            bid.setState(Bid.BidState.ACCEPT);
            em.merge(bid);

            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Couldn't approve receipt: " + e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }
}