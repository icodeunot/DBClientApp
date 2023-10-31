package DBClientApp.model;

/**
 * Customer class. Class to build the customer object.
 */
public class Customer {

    // Customer Attributes
    private int customerID;
    private int divisionID;
    private String customerName;
    private String address;
    private String countryName;
    private String postalCode;
    private String phone;

    /**
     * Customer constructor. Used to create the customer object.
      * @param customerName
     * @param customerID
     * @param address
     * @param postalCode
     * @param phone
     * @param countryName
     * @param divisionID
     */
    public Customer(String customerName, int customerID, String address, String postalCode, String phone, String countryName, int divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.countryName = countryName;
        this.divisionID = divisionID;
    }

    /**
     * getDivisionID. Gets division ID.
      * @return divisionID
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     *getCustomerID. Gets customer ID.
     * @return customerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * getAddy. Formats the street address, first level division, and country into a string.
     * @return streetAddy
     */
    public String getAddy() {                        // Used for address formatting in the Customer Table
        String[] addy = address.split(",");
        String streetAddy = addy[0];
        return streetAddy;
    }

    /**
     * getCountryName. Gets country name.
     * @return countryName
     */
    public String getCountryName() {
        return countryName; }

    /**
     * getCustomerName. Gets customer Name.
     * @return customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * getPostalCode. Gets postal code.
     * @return postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * getPhone. Gets phone number.
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * toString. Overridden string method for the Customer combobox. Otherwise returns a memory location.
      * @return
     */
    @Override
    public String toString() {
        return customerName;
    }

    // Unused code
    // Unused setters
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    // Unused getters
    public String getAddress() {
      return address;
  }
}
