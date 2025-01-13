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
    // Relación con Lot
    private List<Lot> lots;

    // Constructor vacío
    public Auctioneer() {
        this.lots = new ArrayList<>();
    }

    // Constructor con todos los atributos
    public Auctioneer(int id,String dni, String name, String lastName, String email, String password, String phoneNumber) {
        super(id, dni, name, lastName, email, password, phoneNumber);
        this.lots = new ArrayList<>();
    }

    // Getters y Setters para Lots

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
