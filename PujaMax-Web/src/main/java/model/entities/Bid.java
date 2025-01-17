package model.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Bid {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idBid")
	private int idBid;

	@Column(name = "dateBid")
	private Date dateBid;
	
	@Column(name = "price")
	private Double price;
	
	@Column(name = "state")
    private String state;

	public Bid() {
		
	} 
	
	public Bid(Date dateBid, Double price, String state) {
		super();
		this.dateBid = dateBid;
		this.price = price;
		this.state = state;
	}

	public Date getDateBid() {
		return dateBid;
	}

	public void setDateBid(Date dateBid) {
		this.dateBid = dateBid;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
    public String toString() {
        return "Bid{" +
                "dateBid=" + dateBid +
                ", price='" + price + '\'' +
                ", state=" + state +
                '}';
    }
	
	@Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
	
	
}
