package model.entities;

import java.util.Date;

public class Lot {
    private int idLot;
    private String title;
    private int quantityProducts;
    private Date dateOpening;
    private Date dateClosing;
    private String city;
    private Address address; // Relación con Address
    private String state; // Estado del lote (e.g., "Activo", "Inactivo")
    private Auctioneer auctioneer; // Relación con Auctioneer// Relación con Address

    // Constructor vacío
    public Lot() {
    }

    // Constructor con todos los atributos
    public Lot(int idLot, String title, int quantityProducts,Date dateOpening, Date dateClosing, String city, Address address, String state, Auctioneer auctioneer) {
        this.idLot = idLot;
        this.title = title;
        this.quantityProducts = quantityProducts;
        this.dateOpening = dateOpening;
        this.dateClosing = dateClosing;
        this.city = city;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
}

