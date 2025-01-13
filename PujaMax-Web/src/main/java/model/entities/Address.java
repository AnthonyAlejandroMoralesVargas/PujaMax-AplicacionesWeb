package model.entities;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
public class Address implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAddress")
    private int idAddress;

    @Column(name = "name")
    private String name;

    @Column(name = "province")
    private String province;

    @Column(name = "city")
    private String city;

    @Column(name = "mainStreet")
    private String mainStreet;

    @Column(name = "secondaryStreet")
    private String secondaryStreet;

    @Column(name = "postcode")
    private String postcode;

    @Column(name = "houseNumber")
    private String houseNumber;

    @Column(name = "company")
    private String company;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "idAuctioneer")
    private Auctioneer auctioneer;

    public Address() {
    }

    public Address(int idAddress, String name, String province, String city, String mainStreet, String secondaryStreet, String postcode, String houseNumber, String company, Auctioneer auctioneer) {
        this.idAddress = idAddress;
        this.name = name;
        this.province = province;
        this.city = city;
        this.mainStreet = mainStreet;
        this.secondaryStreet = secondaryStreet;
        this.postcode = postcode;
        this.houseNumber = houseNumber;
        this.company = company;
        this.auctioneer = auctioneer;
    }

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }

    public Auctioneer getAuctioneer() {
        return auctioneer;
    }

    public void setAuctioneer(Auctioneer auctioneer) {
        this.auctioneer = auctioneer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMainStreet() {
        return mainStreet;
    }

    public void setMainStreet(String mainStreet) {
        this.mainStreet = mainStreet;
    }

    public String getSecondaryStreet() {
        return secondaryStreet;
    }

    public void setSecondaryStreet(String secondaryStreet) {
        this.secondaryStreet = secondaryStreet;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Address{" +
                "idAddress=" + idAddress +
                ", auctioneer=" + auctioneer +
                ", name='" + name + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", mainStreet='" + mainStreet + '\'' +
                ", secondaryStreet='" + secondaryStreet + '\'' +
                ", postcode='" + postcode + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", company='" + company + '\'' +
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