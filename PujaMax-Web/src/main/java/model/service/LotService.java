package model.service;

import model.dao.LotDAO;
import model.entities.Lot;

import java.sql.SQLException;
import java.util.List;

public class LotService {

    private final LotDAO lotDAO;

    public LotService() {
        lotDAO = new LotDAO();
    }

    public List<Lot> findLotsByIdAuctioneer(int idAuctioneer) throws SQLException {
        return lotDAO.findLotsByIdAuctioneer(idAuctioneer);
    }

    public boolean createLot(Lot lot) {
        return lotDAO.create(lot);
    }

    public Lot findLotById(int idLot) {
        return lotDAO.findById(idLot);
    }

    public boolean updateLot(Lot lot) {
        return lotDAO.update(lot);
    }

    public boolean removeLot(int idLot) {
        return lotDAO.remove(idLot);
    }

    public List<Lot> findLotsByState(String state) throws SQLException {
        return lotDAO.findLotsByState(state);
    }
}
