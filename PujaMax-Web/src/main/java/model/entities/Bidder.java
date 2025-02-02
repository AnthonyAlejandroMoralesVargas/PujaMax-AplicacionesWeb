package model.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "idBidder"))
})
public class Bidder extends User {
	
	private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "bidder", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<Bid> bids;

	public Bidder() {
    }

    public Bidder(int id, String dni, String name, String lastName, String email, String password, String phoneNumber) {
        super(id, dni, name, lastName, email, password, phoneNumber);
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }
}
