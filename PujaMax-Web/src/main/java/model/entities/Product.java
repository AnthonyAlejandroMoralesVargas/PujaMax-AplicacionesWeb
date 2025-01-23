package model.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @ElementCollection
    @CollectionTable(name = "product_photos", joinColumns = @JoinColumn(name = "idProduct"))
    @Column(name = "base64Photo", columnDefinition = "LONGTEXT")
    private List<String> photos = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "idLot", nullable = false)
    private Lot lot;

    public Product() {
    }

    public Product(int idProduct, Lot lot, String title, String category, double priceInitial, String description) {
        this.idProduct = idProduct;
        this.lot = lot;
        this.title = title;
        this.category = category;
        this.priceInitial = priceInitial;
        this.description = description;
    }

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

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    @Override
    public String toString() {
        return "Product{" +
                "idProduct=" + idProduct +
                ", lot=" + lot +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", priceInitial=" + priceInitial +
                ", description='" + description + '\'' +
                ", photos=" + photos.size() + " photos" +
                '}';
    }
}
