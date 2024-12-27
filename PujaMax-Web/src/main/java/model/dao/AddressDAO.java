package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.bdd.DBConnection;
import model.entities.Address;
0
public class AddressDAO {

    public List<Address> getAddresses() throws SQLException {
        List<Address> addresses = new ArrayList<>();

        String _SQL_GET_ALL = "SELECT * FROM address";

        try (PreparedStatement pstmt = DBConnection.getConnection().prepareStatement(_SQL_GET_ALL);
             ResultSet rs = pstmt.executeQuery()) {

            // Iterar el ResultSet para leer los datos
            while (rs.next()) {
                Address address = new Address();
                address.setIdAddress(rs.getInt("idAddress"));
                address.setName(rs.getString("name"));
                address.setProvince(rs.getString("province"));
                address.setCity(rs.getString("city"));
                address.setMainStreet(rs.getString("mainStreet"));
                address.setSecondaryStreet(rs.getString("secondaryStreet"));
                address.setPostcode(rs.getString("postcode"));
                address.setHouseNumber(rs.getString("houseNumber"));
                address.setCompany(rs.getString("company"));

                addresses.add(address);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            DBConnection.close();
        }

        return addresses;
    }

    public boolean createAddress(Address address) {
        String _SQL_INSERT = "INSERT INTO `address` (`name`, `province`, `city`, `mainStreet`, `secondaryStreet`, `postcode`, `houseNumber`, `company`) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = DBConnection.getConnection().prepareStatement(_SQL_INSERT)) {
            pstmt.setString(1, address.getName());
            pstmt.setString(2, address.getProvince());
            pstmt.setString(3, address.getCity());
            pstmt.setString(4, address.getMainStreet());
            pstmt.setString(5, address.getSecondaryStreet());
            pstmt.setString(6, address.getPostcode());
            pstmt.setString(7, address.getHouseNumber());
            pstmt.setString(8, address.getCompany());

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBConnection.close();
        }
    }

    public boolean updateAddress(Address address) {
        String _SQL_UPDATE = "UPDATE address SET name = ?, province = ?, city = ?, mainStreet = ?, "
                + "secondaryStreet = ?, postcode = ?, houseNumber = ?, company = ? WHERE idAddress = ?";

        try (PreparedStatement pstmt = DBConnection.getConnection().prepareStatement(_SQL_UPDATE)) {
            pstmt.setString(1, address.getName());
            pstmt.setString(2, address.getProvince());
            pstmt.setString(3, address.getCity());
            pstmt.setString(4, address.getMainStreet());
            pstmt.setString(5, address.getSecondaryStreet());
            pstmt.setString(6, address.getPostcode());
            pstmt.setString(7, address.getHouseNumber());
            pstmt.setString(8, address.getCompany());
            pstmt.setInt(9, address.getIdAddress());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBConnection.close();
        }
    }

    public Address findAddressById(int idAddress) {
        Address address = null;
        String _SQL_FIND_ADDRESS_BY_ID = "SELECT * FROM address WHERE idAddress = ?";

        try (PreparedStatement pstmt = DBConnection.getConnection().prepareStatement(_SQL_FIND_ADDRESS_BY_ID)) {
            pstmt.setInt(1, idAddress);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    address = new Address(rs.getInt("idAddress"), rs.getString("name"), rs.getString("province"),
                            rs.getString("city"), rs.getString("mainStreet"), rs.getString("secondaryStreet"),
                            rs.getString("postcode"), rs.getString("houseNumber"), rs.getString("company"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close();
        }

        return address;
    }

    public boolean removeAddress(int idAddress) {
        String _SQL_DELETE = "DELETE FROM address WHERE idAddress = ?";
        try (PreparedStatement pstmt = DBConnection.getConnection().prepareStatement(_SQL_DELETE)) {
            pstmt.setInt(1, idAddress);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBConnection.close();
        }
    }
}