package model.service;

import model.jpa.BidJPA;
import model.entities.Bid;
import java.util.List;

public class BidService {

	private final BidJPA bidJPA;

	public BidService() {
		bidJPA = new BidJPA();
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
		return bidJPA.getBidsByUserId(userId);
	}

}