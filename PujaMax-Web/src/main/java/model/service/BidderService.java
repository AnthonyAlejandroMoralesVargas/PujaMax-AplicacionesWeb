package model.service;

import model.entities.Bidder;
import model.jpa.BidderJPA;
import org.mindrot.jbcrypt.BCrypt;

public class BidderService {
    private final BidderJPA bidderJPA;

    public BidderService() {
        bidderJPA = new BidderJPA();
    }
    public boolean createBidder(Bidder bidder) {
        // Verifica si ya existe un Bidder con el mismo DNI
        if (bidderJPA.findByDni(bidder.getDni()) != null) {
            throw new IllegalArgumentException("El DNI ya está registrado para un postor.");
        }
        bidder.setPassword(hashPassword(bidder.getPassword()));
        return bidderJPA.create(bidder);
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
