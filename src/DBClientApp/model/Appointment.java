package DBClientApp.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Appointment {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private String start;
    private String end;
    private LocalDateTime createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int customerID;
    private int userID;
    private int contactID;
    private String contact;

    public Appointment(int appointmentID, String title, String description, String location, String type, Date start,
                       Date end, Date createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy,
                       int customerID, int userID, int contactID) {

    }

    public Appointment(int appointmentID, String title, String description, String location, String contact,
                       String type, int customerID, int userID, String start, String end) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setCreateDate(LocalDateTime createDate) {
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

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public LocalDateTime getCreateDate() {
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

    public int getCustomerID() {
        return customerID;
    }

    public int getUserID() {
        return userID;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContact() {
        return contact;
    }


}
