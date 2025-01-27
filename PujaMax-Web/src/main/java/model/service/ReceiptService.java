package model.service;

import model.jpa.ReceiptJPA;

import java.util.List;

public class ReceiptService {

    private final ReceiptJPA receiptJPA;

    // Constructor que permite la inyección de ReceiptJPA
    public ReceiptService(ReceiptJPA receiptJPA) {
        this.receiptJPA = receiptJPA;
    }

    public void createPayment(List<String> base64Images, int bidId) {
        receiptJPA.createPayment(base64Images, bidId);
    }
}
