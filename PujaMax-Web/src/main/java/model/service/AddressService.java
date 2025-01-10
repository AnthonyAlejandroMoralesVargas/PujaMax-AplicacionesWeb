package model.service;

import model.jpa.AddressJPA;
import model.entities.Address;

import java.sql.SQLException;
import java.util.List;

public class AddressService {
    private final AddressJPA addressJPA;

    public AddressService() {
        addressJPA = new AddressJPA();
    }

    public List<Address> findAddressesByIdAuctioneer(int idAuctioneer) throws SQLException {
        return addressJPA.findAddressesByIdAuctioneer(idAuctioneer);
    }

    public boolean createAddress(Address address) {
        return addressJPA.createAddress(address);
    }

    public Address findAddressById(int idAddress) {
        return addressJPA.findAddressById(idAddress);
    }

    public boolean updateAddress(Address address) {
        return addressJPA.updateAddress(address);
    }

    public boolean removeAddress(int idAddress) {
        return addressJPA.removeAddress(idAddress);
    }
}
