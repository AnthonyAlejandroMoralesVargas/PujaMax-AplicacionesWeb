package model.service;

import model.jpa.BidJPA;
import model.jpa.ProductJPA;
import model.entities.Bid;
import model.entities.Product;
import java.sql.SQLException;
import java.util.List;

public class BidService {

    private final BidJPA bidJPA;
    private final ProductJPA productJPA;

    public BidService() {
        bidJPA = new BidJPA();
		productJPA = new ProductJPA();
    }

    public List<Product> findProductsByLotId(int idLot) throws SQLException {
        return productJPA.findProductsByLotId(idLot);
    }

    public boolean updateBid(Bid bid) {
        return bidJPA.updateBid(bid);
    }

    public boolean removeBid(int idBid) {
        return bidJPA.removeBid(idBid);
    }
}
