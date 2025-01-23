package model.entities;

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

    @Column(name = "photoBase64", columnDefinition = "LONGTEXT") // Almacena la imagen en base64
    private String base64Photo;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "idLot", nullable = false)
    private Lot lot;

    public Product() {
    }

    public Product(int idProduct, Lot lot, String title, String category, double priceInitial, String description, String base64Photo) {
        this.idProduct = idProduct;
        this.lot = lot;
        this.title = title;
        this.category = category;
        this.priceInitial = priceInitial;
        this.description = description;
        this.base64Photo = base64Photo;
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

    public String getBase64Photo() {
        return base64Photo;
    }

    public void setBase64Photo(String base64Photo) {
        this.base64Photo = base64Photo;
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
                ", base64Photo='" + (base64Photo != null ? "[BASE64]" : "null") + '\'' +
                '}';
    }
}