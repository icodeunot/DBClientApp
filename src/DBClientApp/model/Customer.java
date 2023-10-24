package DBClientApp.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Customer {
    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private Date createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int divisionID;
    private String countryName;

    public Customer(String customerName, int customerID, String address, String postalCode, String phone, String countryName, int divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.countryName = countryName;
        this.divisionID = divisionID;
    }

    // public Customer(String customerName, int customerID, String address, String postalCode, String phone, String countryName) {
    //     this.customerName = customerName;
    //     this.customerID = customerID;
    //     this.address = address;
    //     this.postalCode = postalCode;
    //     this.phone = phone;
    //     this.countryName = countryName;
    // }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public String getAddy() {
        String[] addy = address.split(",");
        String streetAddy = addy[0];
        return streetAddy;
    }

    public String getCountryName() { return countryName; }

}
