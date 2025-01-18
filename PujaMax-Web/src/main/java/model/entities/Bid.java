package model.entities;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Bid {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idBid")
	private int idBid;

	@Column(name = "dateBid")
	private Date dateBid;
	
	@Column(name = "currentPrice")
	private Double currentPrice;
	
	@Column(name = "bid")
	private Double bid;
	
	@Column(name = "state")
    private String state;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "idProduct", nullable = false)
	private Product product;


	public Bid() {
		
	} 
	
	
	public Bid(int idBid, Date dateBid, Double currentPrice, Double bid, String state) {
		super();
		this.idBid = idBid;
		this.dateBid = dateBid;
		this.currentPrice = currentPrice;
		this.bid = bid;
		this.state = state;
	}
	
	
	public int getIdBid() {
		return idBid;
	}


	public void setIdBid(int idBid) {
		this.idBid = idBid;
	}


	public Date getDateBid() {
		return dateBid;
	}


	public void setDateBid(Date dateBid) {
		this.dateBid = dateBid;
	}


	public Double getCurrentPrice() {
		return currentPrice;
	}


	public void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
	}


	public Double getBid() {
		return bid;
	}


	public void setBid(Double bid) {
		this.bid = bid;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public Product getProduct() {
		return product;
	}


	public void setProduct(Product product) {
		this.product = product;
	}
	
	
	@Override
    public String toString() {
        return "Bid{" +
                "idBid=" + idBid +
                ", product=" + product +
                ", dateBid='" + dateBid + '\'' +
                ", currentPrice='" + currentPrice + '\'' +
                ", bid=" + bid +
                ", state='" + state + '\'' +
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
