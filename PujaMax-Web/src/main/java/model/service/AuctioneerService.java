package model.service;

import model.dao.AuctioneerDAO;
import model.entities.Auctioneer;
import org.mindrot.jbcrypt.BCrypt;

public class AuctioneerService {
    private final AuctioneerDAO auctioneerDAO;

    public AuctioneerService() {
        auctioneerDAO = new AuctioneerDAO();
    }

    public boolean createAuctioneer(Auctioneer auctioneer) {

        // Verifica si ya existe un Auctioneer con el mismo DNI
        if (auctioneerDAO.findByDni(auctioneer.getDni()) != null) {
            throw new IllegalArgumentException("El DNI ya está registrado para un subastador.");
        }
        auctioneer.setPassword(hashPassword(auctioneer.getPassword()));
        return auctioneerDAO.create(auctioneer);
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
