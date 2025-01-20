package model.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
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

    @Column(name = "document", nullable = false)
    private String document;

    @Column(name = "date", nullable = false)
    private String date = LocalDateTime.now().toString(); // Establece un valor predeterminado

    @ManyToOne
    @JoinColumn(name = "bid_id", nullable = false)
    private Bid bid;

    public Receipt() {}

    public Receipt(int id, String document, String date, Bid bid) {
        this.id = id;
        this.document = document;
        this.date = date;
        this.bid = bid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
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
        return "Receipt [id=" + id + ", document=" + document + ", date=" + date + ", bid=" + bid + "]";
    }
}