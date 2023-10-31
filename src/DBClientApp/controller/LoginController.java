package DBClientApp.controller;

import DBClientApp.DAO.UserData;
import DBClientApp.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;


/**
 * LoginController class. This class handles the logic for the Login page.
 */
public class LoginController implements Initializable {

    // LoginController Attributes
    Stage stage;
    Scene scene;
    private static String userName;
    private String passWord;

    @FXML private Pane loginPane;
    @FXML private Button loginClearBtn;
    @FXML private Button loginSubmitBtn;
    @FXML private Label headerLabel;
    @FXML private Label loginNameLbl;
    @FXML private Label loginPasswordLbl;
    @FXML private Label loginTimeLbl;
    @FXML private Label loginTimeReadout;
    @FXML private TextField loginUserTxt;
    @FXML private TextField loginPasswordTxt;

    /**
     * onMouseMoved. This event calls validateForm when a user moves their mouse. For ease of submit button enabled.
      * @param event
     */
    @FXML void onMouseMoved(MouseEvent event) {validateForm();}

    /**
     * onKeyPressed. This event allows the call to validateForm when a user tabs through the fields.
     * @param event
     */
    @FXML void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB) {
            validateForm();
        }
    }

    /**
     * onMouseClicked. This event requests the mouse focus when  user clicks out of a text field. Added for visual assurance.
     * @param event
     */
    @FXML void onMouseClicked(MouseEvent event) {
        loginPane.requestFocus();
        validateForm();
    }

    /**
     * loginClearClick. This method clears the text fields.
     * @param event
     */
    @FXML void loginClearClick(ActionEvent event) {
        loginPasswordTxt.clear();
        loginUserTxt.clear();
        validateForm();
    }

    /**
     * getUserName. This method is used to return the username for login purposes.
     * @return userName
     */
    public static String getUserName() {
        return userName;
    }

    /**
     * loginPasswordTxtSearch. This event calls the validateForm method to ensure input is valid.
     * @param event
     */
    @FXML void loginPasswordTxtSearch(ActionEvent event) {
        validateForm();
    }

    /**
     * loginUserTxtSearch. This event calls the validateForm method to ensure input is valid.
     * @param event
     */
    @FXML void loginUserTxtSearch(ActionEvent event) {
        validateForm();
    }

    /**
     * loginSubimtClick. This method calls to the database to compare the entered username and password to those in the table.
     * The method also uses a filewriter to log submission attempts.
     * @param event
     * @throws IOException
     */
    @FXML void loginSubmitClick(ActionEvent event) throws IOException {
        ResourceBundle rb = ResourceBundle.getBundle("DBClientApp.language.Language", Locale.getDefault());
        userName = loginUserTxt.getText();
        passWord = loginPasswordTxt.getText();
        User thisUser = UserData.getThisUser(userName);

        // Time data for Login Activity details
        Timestamp loginTime = Timestamp.valueOf(LocalDateTime.now());
        SimpleDateFormat loginFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm z");
        loginFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String activityLogin = loginFormat.format(loginTime);

        // Login Activity file creation
        FileWriter fw = new FileWriter("login_activity.txt", true);
        PrintWriter pw = new PrintWriter(fw);

        if (thisUser == null || thisUser.getUserName() == null || !thisUser.getUserName().equals(userName)) {
            pw.print("Failed login attempt caused by UserName error, by username: " + userName + " at " + activityLogin + "\n");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(rb.getString("alertTitle"));
            alert.setHeaderText(rb.getString("alertHeader"));
            alert.setContentText(rb.getString("alertContext"));
            alert.showAndWait();
        }
        else if (thisUser.getPassword() == null || !thisUser.getPassword().equals(passWord)) {
            pw.print("Failed login attempt caused by Password error, by username: " + userName + " at " + activityLogin + "\n");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(rb.getString("alertTitle"));
            alert.setHeaderText(rb.getString("alertHeader"));
            alert.setContentText(rb.getString("alertContext"));
            alert.showAndWait();
        }
        else {
            pw.print("Successful login attempt by username: " + userName + " at " + activityLogin + "\n");
            userName = loginUserTxt.getText();
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = new Scene(FXMLLoader.load(getClass().getResource("/DBClientApp/view/Menu.fxml")));
            stage.setTitle("Main Menu");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        }
        pw.close();
    }

    /**
     * validateForm. This method is used to make sure username and password are entered before being able to submit.
      */
    private void validateForm() {
        boolean formValidated = true;
        if (loginUserTxt.getText().isEmpty() ||
            loginUserTxt.getText().isBlank() ||
            loginPasswordTxt.getText().isEmpty() ||
            loginPasswordTxt.getText().isBlank()) {
            formValidated = false;
        }
        loginSubmitBtn.setDisable(!formValidated);
    }

    /**
     * initialize. This method initializes the login page and displays the text per the system language.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Login Controller Launched");
        String timeZone = ZoneId.systemDefault().toString();
        loginTimeReadout.setText(timeZone);
        // Locale.setDefault(new Locale("fr", "FR")); // Set to French for test
        ResourceBundle rb = ResourceBundle.getBundle("DBClientApp.language.Language", Locale.getDefault());
        if (Locale.getDefault().getLanguage().equals("fr")) {
         headerLabel.setText(rb.getString("login"));
         loginNameLbl.setText(rb.getString("userName"));
         loginUserTxt.setPromptText(rb.getString("userNamePrompt"));
         loginPasswordLbl.setText(rb.getString("password"));
         loginPasswordTxt.setPromptText(rb.getString("passwordPrompt"));
         loginTimeLbl.setText(rb.getString("timezone"));
         loginClearBtn.setText(rb.getString("clear"));
         loginSubmitBtn.setText(rb.getString("submit"));
        }
        loginSubmitBtn.setDisable(true);
    }

}
