package model.service;

import model.dao.AuctioneerDAO;
import model.entities.Auctioneer;
import model.entities.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

public class UserService {
    private AuctioneerDAO auctioneerDAO;

    public UserService() {
        auctioneerDAO = new AuctioneerDAO();
    }

    public User authenticate(String dni, String password) {

        try {
            Auctioneer auctioneer = auctioneerDAO.findByDni(dni);
            if (auctioneer != null && BCrypt.checkpw(password, auctioneer.getPassword())) {
                return auctioneer;
            }
            /*
            Bidder bidder = bidderDAO.findByDni(dni);
            if (bidder != null && BCrypt.checkpw(password, bidder.getPassword())) {
                return bidder;
            }
             */
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
