package model.service;

import model.jpa.BidJPA;
import model.jpa.ProductJPA;
import model.entities.Bid;
import model.entities.Product;
import java.sql.SQLException;
import java.util.ArrayList;
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
    
    public boolean updateBid(Bid bid) {
        return bidJPA.updateBid(bid);
    }
    
    public List<Bid> getBidsByUserId(int userId) {
        try {
            return bidJPA.getBidsByUserId(userId); // Llama al método del DAO
        } catch (Exception e) {
            System.err.println("Error while fetching bids for user ID: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public boolean updateBidState(int bidId, Bid.BidState newState) {
        try {
            // Buscar la puja por ID
            Bid bid = bidJPA.findBidById(bidId);
            if (bid == null) {
                System.err.println("Bid not found for ID: " + bidId);
                return false;
            }

            // Actualizar el estado de la puja
            bid.setState(newState);
            return bidJPA.updateBid(bid);
        } catch (Exception e) {
            System.err.println("Error updating bid state: " + e.getMessage());
            return false;
        }
    }

}