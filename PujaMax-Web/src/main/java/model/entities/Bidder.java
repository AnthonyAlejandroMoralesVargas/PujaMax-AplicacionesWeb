package model.entities;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "idBidder"))
})
public class Bidder extends User {
    public Bidder() {
    }

    public Bidder(int id, String dni, String name, String lastName, String email, String password, String phoneNumber) {
        super(id, dni, name, lastName, email, password, phoneNumber);
    }
}

