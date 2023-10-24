package DBClientApp.model;

public class Contact {
    private int contactID;
    private String contactName;
    private String email;

    public Contact(String contactName, int contactID) {
        this.contactID = contactID;
        this.contactName = contactName;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public int getContactID() {
        return contactID;
    }

    public String getContactName() {
        return contactName;
    }

    @Override
    public String toString() {
        return contactName;
    }
}