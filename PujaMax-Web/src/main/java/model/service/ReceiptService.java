package model.service;

import model.jpa.ReceiptJPA;

public class ReceiptService {

    private final ReceiptJPA receiptJPA;

    // Constructor que permite la inyección de ReceiptJPA
    public ReceiptService(ReceiptJPA receiptJPA) {
        this.receiptJPA = receiptJPA;
    }

    public void createPayment(String filePath, int bidId) {
        receiptJPA.createPayment(filePath, bidId);
    }
}
