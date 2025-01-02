package model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Auctioneer extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    // Relación con Lot
    private List<Lot> lots;

    // Constructor vacío
    public Auctioneer() {
        this.lots = new ArrayList<>();
    }

    // Constructor con todos los atributos
    public Auctioneer(String dni, String name, String lastName, String email, String password, String phoneNumber) {
        super(dni, name, lastName, email, password, phoneNumber);
        this.lots = new ArrayList<>();
    }

    // Getters y Setters para Lots
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
