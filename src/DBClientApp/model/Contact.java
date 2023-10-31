package DBClientApp.model;


/**
 * Contact class. This class handles the Contact object details.
 */
public class Contact {

    // Contact Variables
    private int contactID;
    private String contactName;
    private String email;

    /**
     * Contact. Constructor used throughout the program for the Contact object.
      * @param contactName
     * @param contactID
     */
    public Contact(String contactName, int contactID) {
        this.contactID = contactID;
        this.contactName = contactName;
    }

    /**
     * toString. Overridden method to return contact name for combobox. Otherwise returns a memory location.
      * @return
     */
    @Override
    public String toString() {
        return contactName;
    }

    /**
     * getContactID. Gets contact ID
      * @return contactID
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * getContactName. Gets the contact name.
     * @return contactName
     */
    public String getContactName() {
        return contactName;
    }

    // Unused setters
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
}