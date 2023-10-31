package DBClientApp.model;


/**
 * Appointment class. Contains the methods and attributes for the Appointment object.
 */
public class Appointment {

    // Appointment Attributes
    private int appointmentID;
    private int customerID;
    private int userID;
    private int contactID;
    private int apptCount;
    private String title;
    private String description;
    private String location;
    private String type;
    private String start;
    private String end;
    private String contact;

    /**
     * Appointment constructor. This constructor is used to assure a modified appointment does not trigger an overlap warning.
      * @param type
     * @param apptCount
     */
    public Appointment(String type, int apptCount) {
        this.type = type;
        this.apptCount = apptCount;
    }

    /**
     * Overloaded Appointment Constructor. This is the constructor used for CRUD actions.
     * @param appointmentID
     * @param title
     * @param description
     * @param location
     * @param contact
     * @param type
     * @param customerID
     * @param userID
     * @param start
     * @param end
     */
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

    /**
     * setLocation. sets the location of the appt.
      * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * setStart. Sets the start of the appointment.
     * @param start
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     * getAppointmentID. Gets the appointment ID.
      * @return appointmentID
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * getCustomerID. Gets customer ID.
     * @return customerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * getUserID. Gets the UserID.
     * @return
     */
    public int getUserID() {
        return userID;
    }

    /**
     * getTitle. Gets the appt. title.
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * getDescription. Get appt. description.
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * getLocation. Gets the appt. location.
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     * getType. Gets the appt. type.
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * getStart. Gets the appt. start details. Date and time.
     * @return start
     */
    public String getStart() {
        return start;
    }

    /**
     * getEnd. Gets the appt. end detals. Date and time.
     * @return end
     */
    public String getEnd() {
        return end;
    }

    /**
     * getContact. Gets the appt. contact.
     * @return contact
     */
    public String getContact() {
        return contact;
    }

    /**
     * setContact. Sets the appt. Contact.
     * @param contact
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    // Unused setters
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setEnd(String end) {
        this.end = end;
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

    // Unused getters
    public int getContactID() {
        return contactID;
    }
    public int getApptCount() {
        return apptCount;
    }
}
