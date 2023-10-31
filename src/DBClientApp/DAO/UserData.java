package DBClientApp.DAO;

import DBClientApp.helper.JDBC;
import DBClientApp.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * UserData class. This class is used to connect to the database and manipulate the User table.
 */
public class UserData {

    /**
     * getUsers. This method creates a list of users from a SQL query.
      * @return userListDAO
     */
    public static ObservableList<User> getUsers() {
        ObservableList<User> userListDAO = FXCollections.observableArrayList();
        try {
            String sql = """
                   SELECT *
                   FROM users;
                   """;
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery(sql);
            while (rs.next()) {
                String userName = rs.getString("User_Name");
                int userID = rs.getInt("User_ID");
                User newUser = new User(userName, userID);
                userListDAO.add(newUser);
            }
        } catch(SQLException throwable) {
            throwable.printStackTrace();
        }
        return userListDAO;
    }

    /**
     * getThisUser. This method gets a specific user. Returns null if the user is not found.
      * @param userName
     * @return newUser
     */
    public static User getThisUser(String userName) {
        try {
            String sql = """
                   SELECT *
                   FROM users
                   WHERE User_Name = ?;
                   """;
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String thisUserName = rs.getString("User_Name");
                String thisUserPassword = rs.getString("Password");
                int userID = rs.getInt("User_ID");
                User newUser = new User(thisUserName, userID, thisUserPassword);
                return newUser;
            }
        } catch(SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}

