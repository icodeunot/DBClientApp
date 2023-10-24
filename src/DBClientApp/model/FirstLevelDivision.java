package DBClientApp.model;

import java.sql.Timestamp;
import java.sql.Date;

public class FirstLevelDivision {
    private int divisionID;
    private String division;
    private Date createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    public int countryID;

    public FirstLevelDivision(int divisionID, String division, Date createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int countryID) {
        this.divisionID = divisionID;
        this.division = division;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryID = countryID;
    }

    public FirstLevelDivision(String division) {
        this.division = division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
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

    public void lastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public String getDivision() {
        return division;
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

    public int getCountryID() {
        return countryID;
    }

    @Override
    public String toString() {
        return division;
    }
}
