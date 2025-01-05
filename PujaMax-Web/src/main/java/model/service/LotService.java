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
        try {
            return lotDAO.findLotsByIdAuctioneer(idAuctioneer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean createLot(Lot lot) {
        try {
            return lotDAO.createLot(lot);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Lot findLotById(int idLot) {
        try {
            return lotDAO.findLotById(idLot);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateLot(Lot lot) {
        try {
            return lotDAO.updateLot(lot);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean removeLot(int idLot) {
        try {
            return lotDAO.removeLot(idLot);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
