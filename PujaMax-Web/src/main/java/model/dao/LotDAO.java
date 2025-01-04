package model.dao;

import model.bdd.DBConnection;
import model.entities.Address;
import model.entities.Auctioneer;
import model.entities.Lot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LotDAO {


    public List<Lot> findLotsByIdAuctioneer(int idAuctioneer) throws SQLException {
        List<Lot> lots = new ArrayList<>();
        Date now = new Date();

        String _SQL_GET_BY_AUCTIONEER = "SELECT * FROM lot WHERE idAuctioneer = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(_SQL_GET_BY_AUCTIONEER)) {

            pstmt.setInt(1, idAuctioneer);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Instanciar objetos
                    Lot lot = new Lot();
                    Auctioneer auctioneer = new Auctioneer();
                    Address address = new Address();
                    // Setear los IDs de los objetos parciales
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

                    // Verificar si debería estar "ACTIVE" o "INACTIVE"
                    boolean shouldBeActive = !now.before(lot.getDateOpening())
                            && !now.after(lot.getDateClosing());

                    // Si hay discrepancia entre shouldBeActive y lot.state, actualizar en BD
                    if (shouldBeActive && "INACTIVE".equals(lot.getState())) {
                        lot.setState("ACTIVE");
                        // Actualiza sólo el campo state en la BD
                        updateLotState(conn, lot);
                    } else if (!shouldBeActive && "ACTIVE".equals(lot.getState())) {
                        lot.setState("INACTIVE");
                        updateLotState(conn, lot);
                    }
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

    private void updateLotState(Connection conn, Lot lot) throws SQLException {
        String _SQL_UPDATE_STATE = "UPDATE lot SET state = ? WHERE idLot = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(_SQL_UPDATE_STATE)) {
            pstmt.setString(1, lot.getState());
            pstmt.setInt(2, lot.getIdLot());
            pstmt.executeUpdate();
        }
        // No se cierra 'conn' aquí porque se maneja en findLotsByIdAuctioneer
    }

    public boolean createLot(Lot lot) throws SQLException {
        String _SQL_INSERT = "INSERT INTO lot  (title, quantityProducts, dateOpening, dateClosing, city, idAddress, idAuctioneer) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = null;

        try {
            pstmt = DBConnection.getConnection().prepareStatement(_SQL_INSERT);
            pstmt.setString(1, lot.getTitle());
            pstmt.setInt(2, lot.getQuantityProducts());
            pstmt.setDate(3, new java.sql.Date(lot.getDateOpening().getTime()));
            pstmt.setDate(4, new java.sql.Date(lot.getDateClosing().getTime()));
            pstmt.setString(5, lot.getCity());
            pstmt.setInt(6, lot.getAddress().getIdAddress());
            pstmt.setInt(7, lot.getAuctioneer().getId());
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBConnection.close(pstmt);
            DBConnection.close();
        }
    }

    public Lot findLotById(int idLot) throws SQLException {
        String _SQL_FIND_LOT_BY_ID = "SELECT * FROM lot WHERE idLot = ?";
        Lot lot = null;
        Date now = new Date();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {pstmt = DBConnection.getConnection().prepareStatement(_SQL_FIND_LOT_BY_ID);
            pstmt.setInt(1, idLot);
            rs = pstmt.executeQuery();
            if (rs.next()){
                // Instanciar objetos
                lot = new Lot();
                Auctioneer auctioneer = new Auctioneer();
                Address address = new Address();
                // Setear los IDs de los objetos parciales
                auctioneer.setId(rs.getInt("idAuctioneer"));
                address.setIdAddress(rs.getInt("idAddress"));
                // Setear los atributos a lot
                lot.setIdLot(rs.getInt("idLot"));
                lot.setTitle(rs.getString("title"));
                lot.setQuantityProducts(rs.getInt("quantityProducts"));
                lot.setDateOpening(rs.getDate("dateOpening"));
                lot.setDateClosing(rs.getDate("dateClosing"));
                lot.setCity(rs.getString("city"));
                lot.setState(rs.getString("state"));
                lot.setAuctioneer(auctioneer);
                lot.setAddress(address);

                // Verificar si debería estar "ACTIVE" o "INACTIVE"
                boolean shouldBeActive = !now.before(lot.getDateOpening())
                        && !now.after(lot.getDateClosing());

                // Si hay discrepancia entre shouldBeActive y lot.state, actualizar en BD
                if (shouldBeActive && "INACTIVE".equals(lot.getState())) {
                    lot.setState("ACTIVE");
                    // Actualiza sólo el campo state en la BD
                    updateLotState(DBConnection.getConnection(), lot);
                } else if (!shouldBeActive && "ACTIVE".equals(lot.getState())) {
                    lot.setState("INACTIVE");
                    updateLotState(DBConnection.getConnection(), lot);
                }
            }
            return lot;
        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }finally {
            DBConnection.close(pstmt);
            DBConnection.close();
        }
    }

    public boolean updateLot(Lot lot) throws SQLException {
        String _SQL_UPDATE = "UPDATE lot SET title = ?, quantityProducts = ?, dateOpening = ?, dateClosing = ?, city = ?, idAddress = ? WHERE idLot = ?";
        PreparedStatement pstmt = null;

        try {
            pstmt = DBConnection.getConnection().prepareStatement(_SQL_UPDATE);
            pstmt.setString(1, lot.getTitle());
            pstmt.setInt(2, lot.getQuantityProducts());
            pstmt.setDate(3, new java.sql.Date(lot.getDateOpening().getTime()));
            pstmt.setDate(4, new java.sql.Date(lot.getDateClosing().getTime()));
            pstmt.setString(5, lot.getCity());
            pstmt.setInt(6, lot.getAddress().getIdAddress());
            pstmt.setInt(7, lot.getIdLot());
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBConnection.close(pstmt);
            DBConnection.close();
        }
    }

    public boolean removeLot(int idLot) throws SQLException {
        String _SQL_DELETE = "DELETE FROM lot WHERE idLot = ?";
        PreparedStatement pstmt = null;

        try {
            pstmt = DBConnection.getConnection().prepareStatement(_SQL_DELETE);
            pstmt.setInt(1, idLot);
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBConnection.close(pstmt);
            DBConnection.close();
        }
    }
}
