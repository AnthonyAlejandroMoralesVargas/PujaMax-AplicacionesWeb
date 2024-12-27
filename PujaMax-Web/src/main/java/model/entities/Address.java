package model.entities;

import java.io.Serializable;

public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idAddress;
    private String name;
    private String province;
    private String city;
    private String mainStreet;
    private String secondaryStreet;
    private String postcode;
    private String houseNumber;
    private String company;

    public Address() {
    }

    public Address(int idAddress, String name, String province, String city, String mainStreet, String secondaryStreet,
                   String postcode, String houseNumber, String company) {
        super();
        this.idAddress = idAddress;
        this.name = name;
        this.province = province;
        this.city = city;
        this.mainStreet = mainStreet;
        this.secondaryStreet = secondaryStreet;
        this.postcode = postcode;
        this.houseNumber = houseNumber;
        this.company = company;
    }

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
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

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}