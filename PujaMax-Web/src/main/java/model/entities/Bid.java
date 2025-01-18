package model.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

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
	
	@Column(name = "price")
	private Double price;
	
	@Column(name = "state")
    private String state;
	
	@ManyToOne
	@JoinColumn(name = "idProduct")
	private Product product;


	

	public Bid() {
		
	} 

	public Date getDateBid() {
		return dateBid;
	}


	public Bid(int idBid, Date dateBid, Double currentPrice, Double price, String state, Product product) {
		super();
		this.idBid = idBid;
		this.dateBid = dateBid;
		this.currentPrice = currentPrice;
		this.price = price;
		this.state = state;
		this.product = product;
	}

	public Product getProduct() {
			return product;
		}
	
		public void setProduct(Product product) {
			this.product = product;
		}
	public int getIdBid() {
		return idBid;
	}

	public void setIdBid(int idBid) {
		this.idBid = idBid;
	}

	public Double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
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
                ", product=" + product +
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
