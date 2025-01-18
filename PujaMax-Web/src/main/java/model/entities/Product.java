package model.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProduct")
    private int idProduct;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "priceInitial", nullable = false)
    private double priceInitial;

    @Column(name = "description")
    private String description;

    @Column(name = "photo", columnDefinition = "LONGBLOB")
    private byte[] photo;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "idLot", nullable = false)
    private Lot lot;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bid> bid = new ArrayList<>();

    
    public Product() {
    }

    // Constructor con todos los atributos
    public Product(int idProduct, Lot lot, String title, String category, double priceInitial, String description, byte[] photo) {
        this.idProduct = idProduct;
        this.lot = lot;
        this.title = title;
        this.category = category;
        this.priceInitial = priceInitial;
        this.description = description;
        this.photo = photo;
    }

    // Getters y setters
    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public Lot getLot() {
        return lot;
    }

    public void setLot(Lot lot) {
        this.lot = lot;
    }
    
    public List<Bid> getBid() {
        return bid;
    }

    public void setBid(List<Bid> bid) {
        this.bid = bid;
    }
    
    public String getBidStatus() {
        if (bid == null || bid.isEmpty()) {
            return "No status available";
        }

        // Obtener la última puja en la lista
        Bid latestBid = bid.get(bid.size() - 1);
        String state = latestBid.getState();
        return (state != null && !state.isEmpty()) ? state : "State not defined";
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPriceInitial() {
        return priceInitial;
    }

    public void setPriceInitial(double priceInitial) {
        this.priceInitial = priceInitial;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
    
    public void addBid(Bid newBid) {
        newBid.setProduct(this); // Establece la relación inversa
        this.bid.add(newBid);    // Agrega la puja al producto
    }


    @Override
    public String toString() {
        return "Product{" +
                "idProduct=" + idProduct +
                ", lot=" + lot +
                ", bid=" + bid +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", priceInitial=" + priceInitial +
                ", description='" + description + '\'' +
                ", photo=" + (photo != null ? "[BLOB]" : "null") +
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
