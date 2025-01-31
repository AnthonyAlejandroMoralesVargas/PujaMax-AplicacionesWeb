package model.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "idAuctioneer"))
})
public class Auctioneer extends User implements Serializable {
    @OneToMany(mappedBy = "auctioneer", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<Lot> lots;

    @OneToMany(mappedBy = "auctioneer", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<Address> addresses;

    // Constructor vacío
    public Auctioneer() {
        this.lots = new ArrayList<>();
    }

    // Constructor con todos los atributos
    public Auctioneer(int id,String dni, String name, String lastName, String email, String password, String phoneNumber) {
        super(id, dni, name, lastName, email, password, phoneNumber);
        this.lots = new ArrayList<>();
        this.addresses = new ArrayList<>();
    }

    // Getters y Setters
    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Lot> getLots() {
        return lots;
    }

    public void setLots(List<Lot> lots) {
        this.lots = lots;
    }

    @Override
    public String toString() {
        return "Auctioneer{" +
                "lots=" + lots +
                "} " + super.toString();
    }
}
