package model.dao;

import model.bdd.DBConnection;
import model.entities.Address;
import model.entities.Auctioneer;
import model.entities.Lot;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LotDAO {


    public List<Lot> findLotsByIdAuctioneer(int idAuctioneer) throws SQLException {
        List<Lot> lots = new ArrayList<>();

        String _SQL_GET_BY_AUCTIONEER = "SELECT * FROM lot WHERE idAuctioneer = ?";

        try (PreparedStatement pstmt = DBConnection.getConnection().prepareStatement(_SQL_GET_BY_AUCTIONEER)) {
            pstmt.setInt(1, idAuctioneer);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Lot lot = new Lot();
                    Auctioneer auctioneer = new Auctioneer();
                    Address address = new Address();
                    // Creación de objetos parciales para evitar realizar una llamada a otro DAO y romper la conexión
                    auctioneer.setId(rs.getInt("idAuctioneer"));
                    address.setIdAddress(rs.getInt("idAddress"));
                    // Setear los atributos de los objetos parciales
                    lot.setIdLot(rs.getInt("idLot"));
                    lot.setTitle(rs.getString("title"));
                    lot.setQuantityProducts(rs.getInt("quantityProducts"));
                    lot.setDateOpening(rs.getDate("dateOpening"));
                    lot.setDateClosing(rs.getDate("dateClosing"));
                    lot.setCity(rs.getString("city"));
                    lot.setState(rs.getString("state"));
                    lot.setAuctioneer(auctioneer);
                    lot.setAddress(address);
                    lots.add(lot);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            DBConnection.close();
        }

        return lots;
    }
}
