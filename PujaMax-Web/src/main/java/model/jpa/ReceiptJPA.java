package model.jpa;

import java.time.LocalDateTime;
import jakarta.persistence.*;

import model.entities.Bid;
import model.entities.Receipt;

public class ReceiptJPA {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("BidMax");

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public ReceiptJPA() {
		super();
	}



	public void createPayment(String filePath, int bidId) {
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("The file path cannot be null or blank.");
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

            bid.setState(Bid.BidState.PENDING_DELIVERY);

            // Crear y guardar el recibo
            Receipt receipt = new Receipt();
            receipt.setDocument(filePath);
            receipt.setBid(bid);
            receipt.setDate(LocalDateTime.now().toString());

            entityManager.persist(receipt);

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }
}
