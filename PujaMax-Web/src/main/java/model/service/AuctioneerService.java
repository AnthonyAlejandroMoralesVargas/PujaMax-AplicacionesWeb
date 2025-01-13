package model.service;

import model.dao.AuctioneerDAO;
import model.entities.Auctioneer;
import org.mindrot.jbcrypt.BCrypt;


import java.sql.SQLException;

public class AuctioneerService {
    private final AuctioneerDAO auctioneerDAO;

    public AuctioneerService() {
        auctioneerDAO = new AuctioneerDAO();
    }

    public boolean createAuctioneer(Auctioneer auctioneer) {
        try {
            auctioneer.setPassword(hashPassword(auctioneer.getPassword()));
            return auctioneerDAO.create(auctioneer);

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
