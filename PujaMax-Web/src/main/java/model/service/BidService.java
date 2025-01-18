package model.service;

import model.jpa.BidJPA;
import model.jpa.ProductJPA;
import model.entities.Bid;
import model.entities.Lot;
import model.entities.Product;
import java.sql.SQLException;
import java.util.List;

import jakarta.persistence.EntityManager;

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
    
    public Product findProductById(int idProduct) {
        return productJPA.findProductById(idProduct);
    }


    public boolean saveBid(Bid bid) {
        return bidJPA.saveBid(bid);
    }
    
    public List<Bid> findBidsByProductId(int productId) {
        return bidJPA.findBidsByProductId(productId);
    }
    
    public boolean createBid(Bid bid) {
        return bidJPA.createBid(bid);
    }
    


}
