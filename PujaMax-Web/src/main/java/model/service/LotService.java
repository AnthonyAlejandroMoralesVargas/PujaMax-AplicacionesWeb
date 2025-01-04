package model.service;

import model.dao.LotDAO;
import model.entities.Lot;

import java.sql.SQLException;
import java.util.List;

public class LotService {

    private LotDAO lotDAO;

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
}
