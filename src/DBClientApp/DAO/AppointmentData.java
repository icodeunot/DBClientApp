package DBClientApp.DAO;

import DBClientApp.helper.JDBC;
import DBClientApp.model.Appointment;
import DBClientApp.model.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AppointmentData {

    // Function to grab all appointments
    public static ObservableList<Appointment> getAllApps() {
        // Create a list to hold the data
        ObservableList <Appointment> appsListDAO = FXCollections.observableArrayList();
        //Timestamp ts = null;
        LocalDateTime startTS = null;
        // Pull the data
        try {
            // SQL query that joins contact table for the contact name column
            String sql = """
                    SELECT *
                    FROM client_schedule.appointments a
                    INNER JOIN client_schedule.contacts ct ON a.Contact_ID = ct.Contact_ID
                    ORDER BY a.Start asc;
                    """;
            // Send the query to the database as a prepared statement
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            // Generate as a result set
            ResultSet rs = ps.executeQuery(sql);
            // Assign results to columns with a while loop
            while (rs.next()) {
                int appointmentID = rs.getInt("a.Appointment_ID");
                String title = rs.getString("a.Title");
                String description = rs.getString("a.Description");
                String location = rs.getString("a.Location");
                String contact = rs.getString("ct.Contact_Name");
                String type = rs.getString("a.Type");
                startTS = rs.getTimestamp("a.Start").toLocalDateTime();
                LocalDate startDate = startTS.toLocalDate();
                LocalTime startTime = startTS.toLocalTime();
                String start = startDate.toString() + " " + startTime.toString();
                LocalDateTime endTS = rs.getTimestamp("a.End").toLocalDateTime();
                LocalDate endDate = endTS.toLocalDate();
                LocalTime endTime = endTS.toLocalTime();
                String end = endDate.toString() + " " + endTime.toString();
                int customerID = rs.getInt("a.Customer_ID");
                int userID = rs.getInt("a.User_ID");
                //ts = rs.getTimestamp("a.start");

                // Make an instance of the Appointment class
                Appointment newAppointment = new Appointment(appointmentID, title, description, location,
                        contact, type, customerID, userID, start, end);
                // Add the instance to the DAO list
                appsListDAO.add(newAppointment);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return appsListDAO;
    }

    // Function to grab weekly appointments
    public static ObservableList<Appointment> getWeekApps() {
        // Create a list to hold the data
        ObservableList <Appointment> appsListDAO = FXCollections.observableArrayList();
        //Timestamp ts = null;
        LocalDateTime startTS = null;
        // Pull the data
        try {
            // SQL query that joins contact table for the contact name column
            String sql = """
                    SELECT * FROM appointments a
                    INNER JOIN contacts ct ON a.Contact_ID = ct.Contact_ID
                    WHERE YEARWEEK(START) = YEARWEEK(NOW())
                    ORDER BY a.Start;
                    """;
            // Send the query to the database as a prepared statement
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            // Generate as a result set
            ResultSet rs = ps.executeQuery(sql);
            // Assign results to columns with a while loop
            while (rs.next()) {
                int appointmentID = rs.getInt("a.Appointment_ID");
                String title = rs.getString("a.Title");
                String description = rs.getString("a.Description");
                String location = rs.getString("a.Location");
                String contact = rs.getString("ct.Contact_Name");
                String type = rs.getString("a.Type");
                startTS = rs.getTimestamp("a.Start").toLocalDateTime();
                LocalDate startDate = startTS.toLocalDate();
                LocalTime startTime = startTS.toLocalTime();
                String start = startDate.toString() + " " + startTime.toString();
                LocalDateTime endTS = rs.getTimestamp("a.End").toLocalDateTime();
                LocalDate endDate = endTS.toLocalDate();
                LocalTime endTime = endTS.toLocalTime();
                String end = endDate.toString() + " " + endTime.toString();
                int customerID = rs.getInt("a.Customer_ID");
                int userID = rs.getInt("a.User_ID");
                //ts = rs.getTimestamp("a.start");

                // Make an instance of the Appointment class
                Appointment newAppointment = new Appointment(appointmentID, title, description, location,
                        contact, type, customerID, userID, start, end);
                // Add the instance to the DAO list
                appsListDAO.add(newAppointment);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return appsListDAO;
    }

    // Function to grab monthly appointments in the database
    public static ObservableList<Appointment> getMonthApps() {
        // Create a list to hold the data
        ObservableList <Appointment> appsListDAO = FXCollections.observableArrayList();
        //Timestamp ts = null;
        LocalDateTime startTS = null;
        // Pull the data
        try {
            // SQL query that joins contact table for the contact name column
            String sql = """
                    SELECT * FROM appointments a
                    INNER JOIN contacts ct ON a.Contact_ID = ct.Contact_ID
                    WHERE MONTH(START) = MONTH(NOW()) AND YEAR(Start) = YEAR(now())
                    ORDER BY a.Start;
                    """;
            // Send the query to the database as a prepared statement
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            // Generate as a result set
            ResultSet rs = ps.executeQuery(sql);
            // Assign results to columns with a while loop
            while (rs.next()) {
                int appointmentID = rs.getInt("a.Appointment_ID");
                String title = rs.getString("a.Title");
                String description = rs.getString("a.Description");
                String location = rs.getString("a.Location");
                String contact = rs.getString("ct.Contact_Name");
                String type = rs.getString("a.Type");
                startTS = rs.getTimestamp("a.Start").toLocalDateTime();
                LocalDate startDate = startTS.toLocalDate();
                LocalTime startTime = startTS.toLocalTime();
                String start = startDate.toString() + " " + startTime.toString();
                LocalDateTime endTS = rs.getTimestamp("a.End").toLocalDateTime();
                LocalDate endDate = endTS.toLocalDate();
                LocalTime endTime = endTS.toLocalTime();
                String end = endDate.toString() + " " + endTime.toString();
                int customerID = rs.getInt("a.Customer_ID");
                int userID = rs.getInt("a.User_ID");
                //ts = rs.getTimestamp("a.start");

                // Make an instance of the Appointment class
                Appointment newAppointment = new Appointment(appointmentID, title, description, location,
                                                             contact, type, customerID, userID, start, end);
                // Add the instance to the DAO list
                appsListDAO.add(newAppointment);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return appsListDAO;
    }

    public static int insertAppointment(String title, String description, String location, String type,
                                        Timestamp startTS, Timestamp endTS, int custID, int userID, int contactID) throws SQLException {
        String sql = "INSERT INTO APPOINTMENTS (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, startTS);
        ps.setTimestamp(6, endTS);
        ps.setInt(7, custID);
        ps.setInt(8, userID);
        ps.setInt(9, contactID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    // Erases all appointments for a customer from the customer table
    public static int deleteAppt(int custID) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, custID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    // Erases a single appointment from the appointment table
    public static int deleteOneAppt(int apptID) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, apptID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int updateAppointment(String title, String description, String location, String type,
                                     Timestamp startTS, Timestamp endTS, int custID, int userID, int contactID, int appointmentID) throws SQLException {
        String sql = """
                     UPDATE appointments
                     SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ?
                     WHERE Appointment_ID = ?
                     """;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, startTS);
        ps.setTimestamp(6, endTS);
        ps.setInt(7, custID);
        ps.setInt(8, userID);
        ps.setInt(9, contactID);
        ps.setInt(10, appointmentID);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    // Report queries
    public static ObservableList<Appointment> getTypeApps(int custID) {
        // Create a list to hold the data
        ObservableList <Appointment> appsListDAO = FXCollections.observableArrayList();
        // Pull the data
        try {
            // SQL query that joins contact table for the contact name column
            String sql = """
                    SELECT Type, COUNT(*) AS Type_Count
                    FROM appointments
                    WHERE Customer_ID = ?
                    GROUP BY Type;
                    """;
            // Send the query to the database as a prepared statement
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, custID);
            // Generate as a result set
            ResultSet rs = ps.executeQuery();
            // Assign results to columns with a while loop
            while (rs.next()) {
                String type = rs.getString("Type");
                int apptCount = rs.getInt("Type_Count");

                // Make an instance of the Appointment class
                Appointment newAppointment = new Appointment(type, apptCount);
                // Add the instance to the DAO list
                appsListDAO.add(newAppointment);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return appsListDAO;
    }


    public static ObservableList<Appointment> getMonthApps(int custID) {
        // Create a list to hold the data
        ObservableList <Appointment> appsListDAO = FXCollections.observableArrayList();
        // Pull the data
        try {
            // SQL query that joins contact table for the contact name column
            String sql = """
                    SELECT DISTINCT(MONTHNAME(Start)) AS Month, Count(*) AS Month_Count
                    FROM appointments
                    WHERE Customer_ID = ?
                    GROUP BY Month;
                    """;
            // Send the query to the database as a prepared statement
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, custID);
            // Generate as a result set
            ResultSet rs = ps.executeQuery();
            // Assign results to columns with a while loop
            while (rs.next()) {
                String type = rs.getString("Month");
                int apptCount = rs.getInt("Month_Count");

                // Make an instance of the Appointment class
                Appointment newAppointment = new Appointment(type, apptCount);
                // Add the instance to the DAO list
                appsListDAO.add(newAppointment);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return appsListDAO;
    }

    public static ObservableList<Appointment> getLocationApps(int custID) {
        // Create a list to hold the data
        ObservableList <Appointment> appsListDAO = FXCollections.observableArrayList();
        // Pull the data
        try {
            // SQL query that joins contact table for the contact name column
            String sql = """
                    SELECT Location, COUNT(*) AS Location_Count
                    FROM appointments
                    WHERE Customer_ID = ?
                    GROUP BY Location;
                    """;
            // Send the query to the database as a prepared statement
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, custID);
            // Generate as a result set
            ResultSet rs = ps.executeQuery();
            // Assign results to columns with a while loop
            while (rs.next()) {
                String type = rs.getString("Location");
                int apptCount = rs.getInt("Location_Count");

                // Make an instance of the Appointment class
                Appointment newAppointment = new Appointment(type, apptCount);
                // Add the instance to the DAO list
                appsListDAO.add(newAppointment);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return appsListDAO;
    }

    public static ObservableList<Appointment> getContactApps(int contactID) {
        // Create a list to hold the data
        ObservableList <Appointment> appsListDAO = FXCollections.observableArrayList();
        //Timestamp ts = null;
        LocalDateTime startTS = null;
        // Pull the data
        try {
            // SQL query that joins contact table for the contact name column
            String sql = """
                    SELECT *
                    FROM client_schedule.appointments a
                    INNER JOIN client_schedule.contacts ct ON a.Contact_ID = ct.Contact_ID
                    WHERE a.Contact_ID = ?
                    ORDER BY a.Start asc;
                    """;
            // Send the query to the database as a prepared statement
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, contactID);
            // Generate as a result set
            ResultSet rs = ps.executeQuery();
            // Assign results to columns with a while loop
            while (rs.next()) {
                int appointmentID = rs.getInt("a.Appointment_ID");
                String title = rs.getString("a.Title");
                String description = rs.getString("a.Description");
                String location = rs.getString("a.Location");
                String contact = rs.getString("ct.Contact_Name");
                String type = rs.getString("a.Type");
                startTS = rs.getTimestamp("a.Start").toLocalDateTime();
                LocalDate startDate = startTS.toLocalDate();
                LocalTime startTime = startTS.toLocalTime();
                String start = startDate.toString() + " " + startTime.toString();
                LocalDateTime endTS = rs.getTimestamp("a.End").toLocalDateTime();
                LocalDate endDate = endTS.toLocalDate();
                LocalTime endTime = endTS.toLocalTime();
                String end = endDate.toString() + " " + endTime.toString();
                int customerID = rs.getInt("a.Customer_ID");
                int userID = rs.getInt("a.User_ID");
                //ts = rs.getTimestamp("a.start");

                // Make an instance of the Appointment class
                Appointment newAppointment = new Appointment(appointmentID, title, description, location,
                        contact, type, customerID, userID, start, end);
                // Add the instance to the DAO list
                appsListDAO.add(newAppointment);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return appsListDAO;
    }

}
