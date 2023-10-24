package DBClientApp.DAO;

import DBClientApp.helper.JDBC;
import DBClientApp.model.Contact;
import DBClientApp.model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactData {
    public static ObservableList<Contact> getContact() {
        ObservableList<Contact> contactListDAO = FXCollections.observableArrayList();
        try {
            String sql = """
                   SELECT *
                   FROM contacts;
                   """;
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery(sql);
            while (rs.next()) {
                String contact = rs.getString("Contact_Name");
                int contactID = rs.getInt("Contact_ID");
                Contact newContact = new Contact(contact, contactID);
                contactListDAO.add(newContact);
                //countryListDAO.add(null);
            }
        } catch(SQLException throwable) {
            throwable.printStackTrace();
        }
        return contactListDAO;
    }
    //public static Country getCustCountry(String countryName) {
    //    Country returnCountry = null;
    //    try {
    //        String sql = """
    //               SELECT *
    //               FROM countries
    //               WHERE Country
    //               LIKE ?;
    //               """;
    //        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
    //        ps.setString(1, countryName);
    //        ResultSet rs = ps.executeQuery();
    //        String custCountry = rs.getString("Country");
    //        int custCountryID = rs.getInt("Country_ID");
    //        returnCountry = new Country(custCountry, custCountryID);
    //    } catch (SQLException throwable) {
    //        throwable.printStackTrace();
    //    }
    //    return returnCountry;
    //}
}
