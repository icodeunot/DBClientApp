package DBClientApp.DAO;

import DBClientApp.helper.JDBC;
import DBClientApp.model.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * DivisionData class. This class handles the First Level Division needs from the database.
 */
public class DivisionData {

    /**
     * getTheseDivisions. Join country and first level division to assign divisions to a chosen country.
     * @param countryID
     * @return divisionListDAO
     */
    public static ObservableList<FirstLevelDivision> getTheseDivisions(int countryID) {
        ObservableList<FirstLevelDivision> divisionListDAO = FXCollections.observableArrayList();

        try {
            String sql = """
                    SELECT *
                    FROM first_level_divisions fld
                    INNER JOIN countries c ON fld.Country_ID = c.Country_ID
                    WHERE c.Country_ID =
                    ?
                    ORDER BY fld.Division asc;
                    """;
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, countryID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String divisionName = rs.getString("fld.Division");
                FirstLevelDivision newDivision = new FirstLevelDivision(divisionName);
                divisionListDAO.add(newDivision);
            }
        } catch(SQLException throwable) {
            throwable.printStackTrace();
        }
        return divisionListDAO;
    }

    /**
     * getDivisions. This method gets all of the divisions from the table.
      * @return divisionListDAO
     */
    public static ObservableList<FirstLevelDivision> getDivisions() {
        ObservableList<FirstLevelDivision> divisionListDAO = FXCollections.observableArrayList();

        try {
            String sql = """
                    SELECT *
                    FROM first_level_divisions fld
                    INNER JOIN countries c ON fld.Country_ID = c.Country_ID
                    ORDER BY fld.Division asc;
                    """;
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String divisionName = rs.getString("fld.Division");
                FirstLevelDivision newDivision = new FirstLevelDivision(divisionName);
                divisionListDAO.add(newDivision);
            }
        } catch(SQLException throwable) {
            throwable.printStackTrace();
        }
        return divisionListDAO;
    }

    /**
     * getDivisionID. This method uses the division name to return the division id.
      * @param divisionName
     * @return divisionID
     */
    public static int getDivisionID(String divisionName) {

        int divisionID = 0;
        try {
            String sql = """
                    SELECT *
                    FROM first_level_divisions
                    WHERE Division LIKE
                    ?;
                    """;
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, divisionName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                divisionID = rs.getInt("Division_ID");
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return divisionID;
    }

    /**
     * getDivisionName. This method uses the division id to get the division name.
      * @param divisionID
     * @return divisionName
     */
    public static String getDivisionName(int divisionID) {
        String divisionName = null;
        try {
            String sql = """
                    SELECT Division
                    FROM first_level_divisions
                    WHERE Division_ID =
                    ?;
                    """;
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, divisionID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                divisionName = rs.getString("Division");
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return divisionName;
    }
}
