package DBClientApp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReportController implements Initializable {

    Stage stage;
    Scene scene;

    @FXML
    private Pane reportPane;

    @FXML
    private Button reportBackBtn;

    @FXML
    private ComboBox<?> reportContactCombo;

    @FXML
    private TableView<?> reportScheduleTable;

    @FXML
    private TableColumn<?, ?> reportScheduleApptCol;

    @FXML
    private TableColumn<?, ?> reportScheduleCustCol;

    @FXML
    private TableColumn<?, ?> reportScheduleDescCol;

    @FXML
    private TableColumn<?, ?> reportScheduleEndCol;

    @FXML
    private TableColumn<?, ?> reportScheduleStartCol;

    @FXML
    private TableColumn<?, ?> reportScheduleTitleCol;

    @FXML
    private TableColumn<?, ?> reportScheduleTypeCol;

    @FXML
    private ComboBox<?> reportMonthCombo;

    @FXML
    private TableView<?> reportMonthTable;

    @FXML
    private TableColumn<?, ?> reportMonthCountCol;

    @FXML
    private TableColumn<?, ?> reportMonthMonthCol;

    @FXML
    private ComboBox<?> reportCountryCombo;

    @FXML
    private TableView<?> reportCountryTable;

    @FXML
    private TableColumn<?, ?> reportCountryCountCol;

    @FXML
    private TableColumn<?, ?> reportCountryCol;

    @FXML
    private ComboBox<?> reportTypeCombo;

    @FXML
    private TableView<?> reportTypeTable;

    @FXML
    private TableColumn<?, ?> reportTypeCountCol;

    @FXML
    private TableColumn<?, ?> reportTypeTypeCol;

    @FXML
    void onMouseClicked(MouseEvent event) {
        reportPane.requestFocus();
    }

    @FXML
    void reportContactComboClick(ActionEvent event) {

    }

    @FXML
    void reportMonthComboClick(ActionEvent event) {

    }

    @FXML
    void reportCountryComboClick(ActionEvent event) {

    }

    @FXML
    void reportTypeComboClick(ActionEvent event) {

    }
    @FXML
    void reportBackClick(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = new Scene(FXMLLoader.load(getClass().getResource("/DBClientApp/view/Menu.fxml")));
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Report Controller Launched");

    }
}
