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

@Entity
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idBid")
    private int idBid;

    @Column(name = "dateBid", nullable = false)
    private Date dateBid;

    @Column(name = "bid", nullable = false)
    private Double bid;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private BidState state;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "idProduct", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "bidder_dni", referencedColumnName = "dni")
    private Bidder bidder;


	// Constructors
    public Bid() {
    }

    public Bid(int idBid, Date dateBid, Double bid, BidState state) {
        this.idBid = idBid;
        this.dateBid = dateBid;
        this.bid = bid;
        this.state = state;
    }
    
    public enum BidState {
        ACTIVE,
        LOST,
        WON,
        //PENDING_APPROVAL,
        PENDING_DELIVERY,
        PAID
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

    public Double getBid() {
        return bid;
    }

    public void setBid(Double bid) {
        this.bid = bid;
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

    @Override
    public String toString() {
        return "Bid{" +
                "idBid=" + idBid +
                ", product=" + product +
                ", dateBid='" + dateBid + '\'' +
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