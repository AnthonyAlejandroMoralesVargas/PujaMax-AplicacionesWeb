package model.dao;

import model.bdd.DBConnection;
import model.entities.Auctioneer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuctioneerDAO {

    public boolean create (Auctioneer auctioneer) throws SQLException {
        String _SQL_INSERT = "INSERT INTO auctioneer (dni, name, lastName, email, password, phoneNumber) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
       PreparedStatement pstmt = null;

        try{
            pstmt = DBConnection.getConnection().prepareStatement(_SQL_INSERT);
            pstmt.setString(1, auctioneer.getDni());
            pstmt.setString(2, auctioneer.getName());
            pstmt.setString(3, auctioneer.getLastName());
            pstmt.setString(4, auctioneer.getEmail());
            pstmt.setString(5, auctioneer.getPassword());
            pstmt.setString(6, auctioneer.getPhoneNumber());
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

    public Auctioneer findByDni(String dni)  throws  SQLException{
        String _SQL_FIND_AUCTIONEER_BY_DNI = "SELECT * FROM auctioneer WHERE dni = ?";
        Auctioneer auctioneer = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = DBConnection.getConnection().prepareStatement(_SQL_FIND_AUCTIONEER_BY_DNI);
            pstmt.setString(1, dni);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                auctioneer = new Auctioneer();
                auctioneer.setId(rs.getInt("id"));
                auctioneer.setDni(rs.getString("dni"));
                auctioneer.setName(rs.getString("name"));
                auctioneer.setLastName(rs.getString("lastName"));
                auctioneer.setEmail(rs.getString("email"));
                auctioneer.setPassword(rs.getString("password"));
                auctioneer.setPhoneNumber(rs.getString("phoneNumber"));
            }
            return auctioneer;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DBConnection.close(pstmt);
            DBConnection.close();
        }
    }

    public Auctioneer findAuctioneerById(int idAuctioneer) throws SQLException {
        String _SQL_FIND_AUCTIONEER_BY_ID = "SELECT * FROM auctioneer WHERE id = ?";
        Auctioneer auctioneer = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = DBConnection.getConnection().prepareStatement(_SQL_FIND_AUCTIONEER_BY_ID);
            pstmt.setInt(1, idAuctioneer);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                auctioneer = new Auctioneer();
                auctioneer.setId(rs.getInt("id"));
                auctioneer.setDni(rs.getString("dni"));
                auctioneer.setName(rs.getString("name"));
                auctioneer.setLastName(rs.getString("lastName"));
                auctioneer.setEmail(rs.getString("email"));
                auctioneer.setPassword(rs.getString("password"));
                auctioneer.setPhoneNumber(rs.getString("phoneNumber"));
            }
            return auctioneer;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DBConnection.close(pstmt);
            DBConnection.close();
        }
    }
}
