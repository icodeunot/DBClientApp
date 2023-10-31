package DBClientApp.DAO;


import DBClientApp.helper.JDBC;
import DBClientApp.model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ContactData class. This class provides the info for the Contact object.
 */
public class ContactData {

    /**
     * getContact. This method gets all contacts from the database.
      * @return contactListDAO
     */
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

}
