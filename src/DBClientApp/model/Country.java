package DBClientApp.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Country {
    private int countryID;
    private String countryName;
    private Date createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;

    public Country(int countryID, String countryName, Date createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy) {
        this.countryID = countryID;
        this.countryName = countryName;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Country(String countryName, int countryID) {
        this.countryName = countryName;
        this.countryID = countryID;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
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

    public int getCountryID() {
        return countryID;
    }

    public String getCountryName() {
        return countryName;
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

    @Override
    public String toString() {
        return countryName;
    }
}


