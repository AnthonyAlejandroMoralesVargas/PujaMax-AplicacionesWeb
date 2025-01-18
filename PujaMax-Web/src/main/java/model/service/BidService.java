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
    
    public Bid findBidById(int idBid) {
        return bidJPA.findBidById(idBid);
    }

   
    public List<Bid> findBidByProductId(int productId) {
        return bidJPA.findBidByProductId(productId);
    }
    
    public boolean createBid(Bid bid) {
        return bidJPA.createBid(bid);
    }
    
    public List<Bid> findActiveBidsByProductId(int productId) {
        return bidJPA.findActiveBidsByProductId(productId);
    }
    
    
    public List<Bid> findBidsByUserId(int userId) {
        return bidJPA.findBidsByUserId(userId);
    }


    public void expireActiveBidsForProduct(int productId) {
        bidJPA.expireActiveBidsForProduct(productId);
    }
    
    public boolean updateBid(Bid bid) {
        return bidJPA.updateBid(bid);
    }
    
   


}