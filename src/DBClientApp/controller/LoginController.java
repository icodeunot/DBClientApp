package DBClientApp.controller;

import DBClientApp.DAO.AppointmentData;
import DBClientApp.DAO.CustomerData;
import DBClientApp.DAO.UserData;
import DBClientApp.model.Appointment;
import DBClientApp.model.Customer;
import DBClientApp.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.awt.event.FocusEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class LoginController implements Initializable {

    Stage stage;
    Scene scene;

    // For upcoming appointment alerts
    private static String userName;
    private String passWord;
    public static String getUserName() {
        return userName;
    }

    @FXML private Pane loginPane;

    @FXML private Label headerLabel;

    @FXML private Button loginClearBtn;

    @FXML private Label loginNameLbl;

    @FXML private Label loginPasswordLbl;

    @FXML private TextField loginPasswordTxt;

    @FXML private Button loginSubmitBtn;

    @FXML private Label loginTimeLbl;

    @FXML private Label loginTimeReadout;

    @FXML private TextField loginUserTxt;

    @FXML void onMouseMoved(MouseEvent event) {validateForm();}

    @FXML void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB) {
            validateForm();
        }
    }

    @FXML void onMouseClicked(MouseEvent event) {
        loginPane.requestFocus();
        validateForm();
    }

    @FXML
    void loginClearClick(ActionEvent event) {
        loginPasswordTxt.clear();
        loginUserTxt.clear();
        validateForm();
    }

    @FXML
    void loginPasswordTxtSearch(ActionEvent event) {
        validateForm();
    }

    @FXML
    void loginUserTxtSearch(ActionEvent event) {
        validateForm();
    }

    @FXML
    void loginSubmitClick(ActionEvent event) throws IOException {
        ResourceBundle rb = ResourceBundle.getBundle("DBClientApp.language.Language", Locale.getDefault());
        userName = loginUserTxt.getText();
        passWord = loginPasswordTxt.getText();
        User thisUser = UserData.getThisUser(userName);


        Timestamp loginTime = Timestamp.valueOf(LocalDateTime.now());
        SimpleDateFormat loginFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm z");
        loginFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String activityLogin = loginFormat.format(loginTime);

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
