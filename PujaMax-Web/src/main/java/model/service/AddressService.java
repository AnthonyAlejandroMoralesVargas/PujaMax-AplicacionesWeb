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
        return addressDAO.findAddressesByIdAuctioneer(idAuctioneer);
    }

    public boolean createAddress(Address address) {
        return addressDAO.create(address);
    }

    public Address findAddressById(int idAddress) {
        return addressDAO.findById(idAddress);
    }

    public boolean updateAddress(Address address) {
        return addressDAO.update(address);
    }

    public boolean removeAddress(int idAddress) {
        return addressDAO.remove(idAddress);
    }
}
