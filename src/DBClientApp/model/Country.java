package DBClientApp.model;

/**
 * Country class. This class provides everything needed for a Country object.
 */
public class Country {

    // Country Attributes
    private int countryID;
    private String countryName;

    /**
     * Country constructor. Builds the country object when called.
      * @param countryName
     * @param countryID
     */
    public Country(String countryName, int countryID) {
        this.countryName = countryName;
        this.countryID = countryID;
    }

    /**
     * toString. Overridden method to provide string value for combo box setting. Otherwise returns memory location.
      * @return countryName
     */
    @Override
    public String toString() {
        return countryName;
    }

    /**
     * getCountryID. gets countryID.
      * @return countryID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * getCountryName. gets countryName.
     * @return countryName
     */
    public String getCountryName() {
        return countryName;
    }

    // Unused Setters
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}


