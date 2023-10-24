package DBClientApp.DAO;

import DBClientApp.helper.JDBC;
import DBClientApp.model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryData {
    public static ObservableList<Country> getCountry() {
        ObservableList<Country> countryListDAO = FXCollections.observableArrayList();

        try {
            String sql = """
                    SELECT *
                    FROM countries
                    ORDER BY Country asc;
                    """;
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery(sql);
            while (rs.next()) {
                String countryName = rs.getString("Country");
                int countryID = rs.getInt("Country_ID");
                Country newCountry = new Country(countryName, countryID);
                countryListDAO.add(newCountry);
            }
        } catch(SQLException throwable) {
            throwable.printStackTrace();
        }
        return countryListDAO;
    }

    public static Country getCustCountry(String countryName) {
        Country returnCountry = null;
        try {
            String sql = """
                    SELECT *
                    FROM countries
                    WHERE Country
                    LIKE ?;
                    """;
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, countryName);
            ResultSet rs = ps.executeQuery();
            String custCountry = rs.getString("Country");
            int custCountryID = rs.getInt("Country_ID");
            returnCountry = new Country(custCountry, custCountryID);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return returnCountry;
    }
}