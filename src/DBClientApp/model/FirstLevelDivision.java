package DBClientApp.model;

/**
 * FirstLevelDivision class. Class used to make FLD objects.
 */
public class FirstLevelDivision {

    // First Level Division Attributes
    public int countryID;
    private int divisionID;
    private String division;

    /**
     * FirstLevelDivision constructor. Used throughout the program to decipher FLD per country.
     * @param division
     */
    public FirstLevelDivision(String division) {
        this.division = division;
    }

    /**
     * getDivision. Gets the division name.
     * @return division
     */
    public String getDivision() {
        return division;
    }

    /**
     * toString. Overrides toString for combo box readout. Otherwise shows the memory location.
      * @return
     */
    @Override
    public String toString() {
        return division;
    }

    // Unused Code
    // Unused constructor
    public FirstLevelDivision(int divisionID, String division, int countryID) {
        this.divisionID = divisionID;
        this.division = division;
        this.countryID = countryID;
    }
    // Unused setters
    public void setDivision(String division) {
        this.division = division;
    }
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
    // Unused getters
    public int getDivisionID() {
        return divisionID;
    }
    public int getCountryID() {
        return countryID;
    }
}
