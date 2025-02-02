package model.service;

import model.entities.Bidder;
import model.dao.BidderDAO;
import org.mindrot.jbcrypt.BCrypt;

public class BidderService {
    private final BidderDAO bidderDAO;

    public BidderService() {
        bidderDAO = new BidderDAO();
    }
    public boolean createBidder(Bidder bidder) {
        // Verifica si ya existe un Bidder con el mismo DNI
        if (bidderDAO.findByDni(bidder.getDni()) != null) {
            throw new IllegalArgumentException("El DNI ya está registrado para un postor.");
        }
        bidder.setPassword(hashPassword(bidder.getPassword()));
        return bidderDAO.create(bidder);
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


}