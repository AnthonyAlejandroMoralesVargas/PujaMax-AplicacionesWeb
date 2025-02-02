package model.service;

import model.dao.ReceiptDAO;

import java.util.List;

public class ReceiptService {

    private final ReceiptDAO receiptDAO;

    public ReceiptService() {
        this.receiptDAO = new ReceiptDAO(); // Se instancia dentro del servicio, no en el controlador
    }

    public void createPayment(List<String> base64Images, int bidId) {
        receiptDAO.createPayment(base64Images, bidId);
    }

    public boolean approveReceipt(int idReceipt) {
        return new ReceiptDAO().approveReceipt(idReceipt);
    }
}
