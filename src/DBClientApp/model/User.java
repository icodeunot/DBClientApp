package DBClientApp.model;

/**
 * User class. This class is the basic model for the user object.
 */
public class User {

    // User Attributes
    private int userID;
    private String userName;
    private String password;

    /**
     * User constructor. Used in most cases throughout the program.
     * @param userName
     * @param userID
     */
    public User(String userName, int userID) {
        this.userID = userID;
        this.userName = userName;
    }

    /**
     * User constructor. Used for the login only to grab the password.
     * @param userName
     * @param userID
     * @param password
     */
    public User(String userName, int userID, String password) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
    }

    /**
     * getUserID. gets the user ID.
     * @return userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * getUserName. gets the username
     * @return userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * getPassword. Gets the user password.
     * @return password
     */
    public String getPassword() {
        return password;
    }

    // Unused Code
    // Unused setters
    public void setUserID(int userID) {
        this.userID = userID;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}