package DBClientApp.controller;

import DBClientApp.DAO.AppointmentData;
import DBClientApp.DAO.ContactData;
import DBClientApp.DAO.CountryData;
import DBClientApp.DAO.CustomerData;
import DBClientApp.model.Appointment;
import DBClientApp.model.Contact;
import DBClientApp.model.Country;
import DBClientApp.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
    private ComboBox<Contact> reportContactCombo;

    @FXML
    private TableView<Appointment> reportScheduleTable;

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
    private ComboBox<Customer> reportMonthCombo;

    @FXML
    private TableView<Appointment> reportMonthTable;

    @FXML
    private TableColumn<?, ?> reportMonthCountCol;

    @FXML
    private TableColumn<?, ?> reportMonthMonthCol;

    @FXML
    private ComboBox<Customer> reportLocationCombo;

    @FXML
    private TableView<Appointment> reportLocationTable;

    @FXML
    private TableColumn<?, ?> reportLocationCountCol;

    @FXML
    private TableColumn<?, ?> reportLocationCol;

    @FXML
    private ComboBox<Customer> reportTypeCombo;

    @FXML
    private TableView<Appointment> reportTypeTable;

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
        if (reportContactCombo.getSelectionModel().getSelectedItem() != null) {
            ObservableList<Appointment> appsList = AppointmentData.getContactApps(reportContactCombo.getSelectionModel().getSelectedItem().getContactID());
            reportScheduleTable.setItems(appsList);
            reportScheduleApptCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            reportScheduleTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            reportScheduleTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            reportScheduleDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            reportScheduleStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            reportScheduleEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
            reportScheduleCustCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        }
    }

    @FXML
    void reportMonthComboClick(ActionEvent event) {
        if (reportMonthCombo.getSelectionModel().getSelectedItem() != null) {
            ObservableList<Appointment> appsList = AppointmentData.getMonthApps(reportMonthCombo.getSelectionModel().getSelectedItem().getCustomerID());
            reportMonthTable.setItems(appsList);
            reportMonthMonthCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            reportMonthCountCol.setCellValueFactory(new PropertyValueFactory<>("apptCount"));
        }
        else {
            reportTypeTable.setPlaceholder(new Label("Please choose a customer to view appointments."));
        }
    }

    @FXML
    void reportLocationComboClick(ActionEvent event) {
        if (reportLocationCombo.getSelectionModel().getSelectedItem() != null) {
            ObservableList<Appointment> appsList = AppointmentData.getLocationApps(reportLocationCombo.getSelectionModel().getSelectedItem().getCustomerID());
            reportLocationTable.setItems(appsList);
            reportLocationCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            reportLocationCountCol.setCellValueFactory(new PropertyValueFactory<>("apptCount"));
        }
        else {
            reportLocationTable.setPlaceholder(new Label("Please choose a customer to view appointments."));
        }
    }

    @FXML
    void reportTypeComboClick(ActionEvent event) {
        if (reportTypeCombo.getSelectionModel().getSelectedItem() != null) {
            ObservableList<Appointment> appsList = AppointmentData.getTypeApps(reportTypeCombo.getSelectionModel().getSelectedItem().getCustomerID());
            reportTypeTable.setItems(appsList);
            reportTypeTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            reportTypeCountCol.setCellValueFactory(new PropertyValueFactory<>("apptCount"));
        }
        else {
            reportTypeTable.setPlaceholder(new Label("Please choose a customer to view appointments."));
        }
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
        // Contact Names
        ObservableList<Contact> contacts = ContactData.getContact();
        reportContactCombo.setItems(contacts);
        // Customers
        ObservableList<Customer> customers = CustomerData.getAllCusts();
        reportTypeCombo.setItems(customers);
        reportMonthCombo.setItems(customers);
        reportLocationCombo.setItems(customers);
        reportTypeTable.setPlaceholder(new Label("Please choose a customer to view appointments."));
        reportMonthTable.setPlaceholder(new Label("Please choose a customer to view appointments."));
        reportLocationTable.setPlaceholder(new Label("Please choose a customer to view appointments."));
        reportScheduleTable.setPlaceholder(new Label("Please choose a customer to view appointments."));
    }
}
