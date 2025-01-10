package model.service;

import model.jpa.LotJPA;
import model.entities.Lot;

import java.sql.SQLException;
import java.util.List;

public class LotService {

    private final LotJPA lotJPA;

    public LotService() {
        lotJPA = new LotJPA();
    }

    public List<Lot> findLotsByIdAuctioneer(int idAuctioneer) throws SQLException {
        return lotJPA.findLotsByIdAuctioneer(idAuctioneer);
    }

    public boolean createLot(Lot lot) {
        return lotJPA.createLot(lot);
    }

    public Lot findLotById(int idLot) {
        return lotJPA.findLotById(idLot);
    }

    public boolean updateLot(Lot lot) {
        return lotJPA.updateLot(lot);
    }

    public boolean removeLot(int idLot) {
        return lotJPA.removeLot(idLot);
    }
}
