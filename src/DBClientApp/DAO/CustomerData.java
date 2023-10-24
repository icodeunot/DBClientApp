package DBClientApp.DAO;

import DBClientApp.model.Customer;
import DBClientApp.helper.JDBC;
import DBClientApp.model.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerData {

    public static ObservableList<Customer> getAllCusts() {
        // Create a list to hold the data
        ObservableList<Customer> custListDAO = FXCollections.observableArrayList();
        // SQL query that joins the location tables in order to show the first level division and country columns
        try {
            String sql = """
                    SELECT * FROM client_schedule.Customers c
                    INNER JOIN client_schedule.first_level_divisions fld ON c.Division_ID = fld.Division_ID
                    INNER JOIN client_schedule.countries co ON fld.COUNTRY_ID = co.Country_ID
                    ORDER BY c.Customer_ID asc;
                    """;
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            //*** Make the query ==> ResultSet
            ResultSet rs = ps.executeQuery();
            //***Cycle through the result
            while (rs.next()) {
                //*** Pull out the data
                String customerName = rs.getString("c.Customer_Name");
                int customerID = rs.getInt("c.Customer_ID");
                String address = rs.getString("c.Address") + ", " + rs.getString("fld.Division");
                String postalCode = rs.getString("c.Postal_Code");
                String phone = rs.getString("c.Phone");
                String countryName = rs.getString("co.Country");
                int divisionID = rs.getInt("fld.Division_ID");

                //*** Make an object instance
                Customer newCustomer = new Customer(customerName, customerID, address, postalCode, phone, countryName, divisionID);
                //*** Add to list
                custListDAO.add(newCustomer);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return custListDAO;
    }

    public static int insertCustomer(String customerName, String address, String postalCode, String phone,
                                     String createdBy, String lastUpdatedBy, int divisionID) throws SQLException {
        String sql = "INSERT INTO CUSTOMERS (Customer_Name, Address, Postal_Code, Phone, Created_By, Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setString(5, createdBy);
        ps.setString(6, lastUpdatedBy);
        ps.setInt(7, divisionID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int updateCustomer(String customerName, String address, String postalCode, String phone,
                                     String user, int divisionID, int customerID) throws SQLException {
        ObservableList<FirstLevelDivision> divisions = DivisionData.getDivisions();
        System.out.println(divisionID);
        String sql = """
                     UPDATE customers
                     SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Updated_By = ?, Division_ID = ?
                     WHERE Customer_ID = ?
                     """;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setString(5, user);
        ps.setInt(6, divisionID);
        ps.setInt(7, customerID);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int delete(int custID) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, custID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

}
