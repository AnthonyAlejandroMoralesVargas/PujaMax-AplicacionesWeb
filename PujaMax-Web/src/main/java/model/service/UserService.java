package model.service;

import model.entities.Bidder;
import model.jpa.AuctioneerJPA;
import model.entities.Auctioneer;
import model.entities.User;
import model.jpa.BidderJPA;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {
    private final AuctioneerJPA auctioneerJPA;
    private final BidderJPA bidderJPA;

    public UserService() {
        auctioneerJPA = new AuctioneerJPA();
        bidderJPA = new BidderJPA();
    }

    public User authenticate(String dni, String password, String role) {
        if ("auctioneer".equalsIgnoreCase(role)) {
            Auctioneer auctioneer = auctioneerJPA.findByDni(dni);
            if (auctioneer != null && BCrypt.checkpw(password, auctioneer.getPassword())) {
                return auctioneer;
            }
        } else if ("bidder".equalsIgnoreCase(role)) {
            Bidder bidder = bidderJPA.findByDni(dni);
            if (bidder != null && BCrypt.checkpw(password, bidder.getPassword())) {
                return bidder;
            }
        }
        return null;
    }
}