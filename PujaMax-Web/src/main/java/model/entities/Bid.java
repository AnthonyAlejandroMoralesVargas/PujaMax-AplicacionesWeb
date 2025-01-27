package model.entities;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
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

    @Column(name = "dateBid", nullable = false)
    private Date dateBid;

    @Column(name = "bid", nullable = false)
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private BidState state;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "idProduct", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "bidder_dni", referencedColumnName = "dni")
    private Bidder bidder;
    
    @OneToOne
    @JoinColumn(name = "idReceipt", nullable = true)
    private Receipt receipt;

	// Constructors
    public Bid() {
    }

    public Bid(int idBid, Date dateBid, Double amount, Product product, Bidder bidder) {
        this.idBid = idBid;
        this.dateBid = dateBid;
        this.amount = amount;
        this.state = BidState.TOP;
        this.product = product;
        this.bidder = bidder;
        this.receipt = null;
    }
    
    public enum BidState {
        TOP,
        SURPASSED,
        WON,
        LOST,
        PENDING_APPROVAL,
        ACCEPT,
        REJECT
    }


    // Getters and Setters
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double bid) {
        this.amount = bid;
    }

    public BidState getState() {
        return state;
    }

    public void setState(BidState state) {
        this.state = state;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Bidder getBidder() {
        return bidder;
    }

    public void setBidder(Bidder bidder) {
        this.bidder = bidder;
    }
    
    

    public Receipt getReceipt() {
		return receipt;
	}

	public void setReceipt(Receipt receipt) {
		this.receipt = receipt;
	}

	@Override
    public String toString() {
        return "Bid{" +
                "idBid=" + idBid +
                ", product=" + product +
                ", dateBid='" + dateBid + '\'' +
                ", bid=" + amount +
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