package model.service;

import model.jpa.ReceiptJPA;

import java.util.List;

public class ReceiptService {

	private final ReceiptJPA receiptJPA;

    public ReceiptService() {
        this.receiptJPA = new ReceiptJPA(); // Se instancia dentro del servicio, no en el controlador
    }

    public void createPayment(List<String> base64Images, int bidId) {
        receiptJPA.createPayment(base64Images, bidId);
    }
    
    public boolean approveReceipt(int idReceipt) {
        return new ReceiptJPA().approveReceipt(idReceipt);
    }
}
