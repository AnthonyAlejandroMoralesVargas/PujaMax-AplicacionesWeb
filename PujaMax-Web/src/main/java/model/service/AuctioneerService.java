package model.service;

import model.jpa.AuctioneerJPA;
import model.entities.Auctioneer;
import org.mindrot.jbcrypt.BCrypt;

public class AuctioneerService {
    private final AuctioneerJPA auctioneerJPA;

    public AuctioneerService() {
        auctioneerJPA = new AuctioneerJPA();
    }

    public boolean createAuctioneer(Auctioneer auctioneer) {

        // Verifica si ya existe un Auctioneer con el mismo DNI
        if (auctioneerJPA.findByDni(auctioneer.getDni()) != null) {
            throw new IllegalArgumentException("El DNI ya está registrado para un subastador.");
        }
        auctioneer.setPassword(hashPassword(auctioneer.getPassword()));
        return auctioneerJPA.create(auctioneer);
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
