package model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "idBidder"))
})
public class Bidder extends User implements Serializable{
	
	// Relación con Lot
    private List<Lot> lots;
	
    public Bidder() {
    	 this.lots = new ArrayList<>();
    }

    public Bidder(int id, String dni, String name, String lastName, String email, String password, String phoneNumber) {
        super(id, dni, name, lastName, email, password, phoneNumber);
        this.lots = new ArrayList<>();
    }
    
    // Getters y Setters para Lots

    public void setLots(List<Lot> lots) {
        this.lots = lots;
    }

    @Override
    public String toString() {
        return "Bidder{" +
                "lots=" + lots +
                "} " + super.toString();
    }
}

