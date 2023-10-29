package DBClientApp.DAO;

import DBClientApp.helper.JDBC;
import DBClientApp.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserData {

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

