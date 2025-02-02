package model.service;

import model.dao.BidDAO;
import model.entities.Bid;
import java.util.List;

public class BidService {

	private final BidDAO bidDAO;

	public BidService() {
		bidDAO = new BidDAO();
	}

	public Bid findBidById(int idBid) {
		return bidDAO.findById(idBid);
	}

	public List<Bid> findBidByProductId(int productId) {
		return bidDAO.findBidByProductId(productId);
	}

	public boolean createBid(Bid bid) {
		return bidDAO.create(bid);
	}

	public boolean updateBid(Bid bid) {
		return bidDAO.update(bid);
	}

	public List<Bid> getBidsByUserId(int userId) {
		return bidDAO.getBidsByUserId(userId);
	}

}