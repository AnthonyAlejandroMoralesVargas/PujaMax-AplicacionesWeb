package model.jpa;

import java.time.LocalDateTime;

import jakarta.persistence.EntityManager;
import model.entities.Bid;
import model.entities.Receipt;

public class ReceiptJPA {

    private EntityManager entityManager;

    // Constructor que recibe el EntityManager
    public ReceiptJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void createPayment(String filePath, int bidId) {
        // Buscar la puja
        Bid bid = entityManager.find(Bid.class, bidId);

        if (bid == null) {
            throw new IllegalArgumentException("Bid not found with ID: " + bidId);
        }

        // Asignar el estado al bid
        String stateString = "PENDING_DELIVERY"; // Esto debería venir de tu lógica
        try {
            Bid.BidState newState = Bid.BidState.valueOf(stateString);
            bid.setState(newState);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid state value: " + stateString, e);
        }

        // Crear y guardar el recibo
        Receipt receipt = new Receipt();
        receipt.setDocument(filePath);
        receipt.setBid(bid);
        receipt.setDate(LocalDateTime.now().toString()); // Asignar la fecha actual

        entityManager.persist(receipt);
    }
}
