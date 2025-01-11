package model.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Lot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLot")
    private int idLot;

    @Column(name = "title")
    private String title;

    @Column(name = "quantityProducts")
    private int quantityProducts;

    @Column(name = "dateOpening")
    private Date dateOpening;

    @Column(name = "dateClosing")
    private Date dateClosing;

    @Column(name = "state")
    private String state; // Estado del lote (e.g., "Activo", "Inactivo")

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "idAddress")
    private Address address;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "idAuctioneer")
    private Auctioneer auctioneer;

    public Lot() {
    }

    // Constructor con todos los atributos
    public Lot(int idLot, String title, int quantityProducts, Date dateOpening, Date dateClosing, Address address, String state, Auctioneer auctioneer) {
        this.idLot = idLot;
        this.title = title;
        this.quantityProducts = quantityProducts;
        this.dateOpening = dateOpening;
        this.dateClosing = dateClosing;
        this.address = address;
        this.state = state;
        this.auctioneer = auctioneer;
    }

    // Getters y setters
    public int getIdLot() {
        return idLot;
    }

    public void setIdLot(int id) {
        this.idLot = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantityProducts() {
        return quantityProducts;
    }

    public void setQuantityProducts(int quantityProducts) {
        this.quantityProducts = quantityProducts;
    }

    public Date getDateOpening() {
        return dateOpening;
    }

    public void setDateOpening(Date dateOpening) {
        this.dateOpening = dateOpening;
    }

    public Date getDateClosing() {
        return dateClosing;
    }

    public void setDateClosing(Date dateClosing) {
        this.dateClosing = dateClosing;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Auctioneer getAuctioneer() {
        return auctioneer;
    }

    public void setAuctioneer(Auctioneer auctioneer) {
        this.auctioneer = auctioneer;
    }

    @Override
    public String toString() {
        return "Lot{" +
                "idLot=" + idLot +
                ", title='" + title + '\'' +
                ", quantityProducts=" + quantityProducts +
                ", dateOpening=" + dateOpening +
                ", dateClosing=" + dateClosing +
                ", state='" + state + '\'' +
                ", address=" + address +
                ", auctioneer=" + auctioneer +
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

