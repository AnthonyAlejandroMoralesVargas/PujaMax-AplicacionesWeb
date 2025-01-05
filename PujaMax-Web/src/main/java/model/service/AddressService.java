package model.service;

import model.dao.AddressDAO;
import model.entities.Address;

import java.sql.SQLException;
import java.util.List;

public class AddressService {
    private final AddressDAO addressDAO;

    public AddressService() {
        addressDAO = new AddressDAO();
    }

    public List<Address> findAddressesByIdAuctioneer(int idAuctioneer) throws SQLException {
        try {
            return addressDAO.findAddressesByIdAuctioneer(idAuctioneer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean createAddress(Address address) {
        return addressDAO.createAddress(address);
    }

    public Address findAddressById(int idAddress) {
        return addressDAO.findAddressById(idAddress);
    }

    public boolean updateAddress(Address address) {
        return addressDAO.updateAddress(address);
    }

    public boolean removeAddress(int idAddress) {
        return addressDAO.removeAddress(idAddress);
    }
}
