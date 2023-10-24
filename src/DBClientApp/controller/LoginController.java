package DBClientApp.controller;

import DBClientApp.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    Stage stage;
    Scene scene;

    @FXML
    private Pane loginPane;

    @FXML
    private Button loginClearBtn;

    @FXML
    private ComboBox<?> loginLanguageCombo;

    @FXML
    private Label loginLanguageLbl;

    @FXML
    private Label loginNameLbl;

    @FXML
    private Label loginPasswordLbl;

    @FXML
    private TextField loginPasswordTxt;

    @FXML
    private Button loginSubmitBtn;

    @FXML
    private Label loginTimeLbl;

    @FXML
    private Label loginTimeReadout;

    @FXML
    private TextField loginUserTxt;

    @FXML
    void onMouseClicked(MouseEvent event) {
        loginPane.requestFocus();
    }

    @FXML
    void loginClearClick(ActionEvent event) {

    }

    @FXML
    void loginLanguageComboClick(ActionEvent event) {

    }

    @FXML
    void loginPasswordTxtSearch(ActionEvent event) {

    }

    @FXML
    void loginSubmitClick(ActionEvent event) throws IOException {
        System.out.println("This message is from the LoginController, loginSubmitClick Event. It is telling you to look " +
                           "at the UpdateCutomerController for the Lambda Requirements");
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = new Scene(FXMLLoader.load(getClass().getResource("/DBClientApp/view/Menu.fxml")));
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void loginUserTxtSearch(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Login Controller Launched");
    }

}
