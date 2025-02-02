package model.service;

import model.entities.Bidder;
import model.dao.AuctioneerDAO;
import model.entities.Auctioneer;
import model.entities.User;
import model.dao.BidderDAO;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {
    private final AuctioneerDAO auctioneerDAO;
    private final BidderDAO bidderDAO;

    public UserService() {
        auctioneerDAO = new AuctioneerDAO();
        bidderDAO = new BidderDAO();
    }

    public User authenticate(String dni, String password, String role) {
        if ("auctioneer".equalsIgnoreCase(role)) {
            Auctioneer auctioneer = auctioneerDAO.findByDni(dni);
            if (auctioneer != null && BCrypt.checkpw(password, auctioneer.getPassword())) {
                return auctioneer;
            }
        } else if ("bidder".equalsIgnoreCase(role)) {
            Bidder bidder = bidderDAO.findByDni(dni);
            if (bidder != null && BCrypt.checkpw(password, bidder.getPassword())) {
                return bidder;
            }
        }
        return null;
    }
}