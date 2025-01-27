package model.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "receipt")
public class Receipt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ElementCollection
    @CollectionTable(name = "receipt_images", joinColumns = @JoinColumn(name = "receipt_id"))
    @Column(name = "image", columnDefinition = "LONGTEXT")
    private List<String> images = new ArrayList<>(); // Maneja múltiples imágenes como Base64 o rutas


    @Column(name = "date", nullable = false)
    private String date = LocalDateTime.now().toString(); // Establece un valor predeterminado

    @ManyToOne
    @JoinColumn(name = "bid_id", nullable = false)
    private Bid bid;

    public Receipt() {}

    public Receipt(int id, List<String> images, String date, Bid bid) {
        this.id = id;
        this.images = images;
        this.date = date;
        this.bid = bid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getImages() {
        return images;
    }
    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Bid getBid() {
        return bid;
    }

    public void setBid(Bid bid) {
        this.bid = bid;
    }

    @Override
    public String toString() {
        return "Receipt [id=" + id + ", images=" + images.size() + " images, date=" + date + ", bid=" + bid + "]";
    }
}