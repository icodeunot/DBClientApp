package DBClientApp.controller;

import DBClientApp.DAO.AppointmentData;
import DBClientApp.DAO.CustomerData;
import DBClientApp.model.Appointment;
import DBClientApp.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    // UI Variables
    Stage stage;
    Scene scene;
    // Item on the Customer table selected
    static Customer chosenCustomer;
    // Item on the Appointment table selected
    static Appointment chosenAppointment;

    @FXML
    private Pane menuPane;
    //************************************************
    // Tables
    //************************************************
    // Appointment table attributes
    @FXML
    private TableView<Appointment> menuApptTable;
    // Appointment table columns
    @FXML
    private TableColumn<?, ?> apptContactCol;
    @FXML
    private TableColumn<?, ?> apptCustIDCol;
    @FXML
    private TableColumn<?, ?> apptDescriptionCol;
    @FXML
    private TableColumn<?, ?> apptEndCol;
    @FXML
    private TableColumn<?, ?> apptIDCol;
    @FXML
    private TableColumn<?, ?> apptLocationCol;
    @FXML
    private TableColumn<?, ?> apptStartCol;
    @FXML
    private TableColumn<?, ?> apptTitleCol;
    @FXML
    private TableColumn<?, ?> apptTypeCol;
    @FXML
    private TableColumn<?, ?> apptUserIDCol;
    //************************************************
    // Customer table attributes
    @FXML
    private TableView<Customer> menuCustTable;
    // Customer table columns
    @FXML
    private TableColumn<?, ?> custAddressCol;
    @FXML
    private TableColumn<?, ?> custCountryCol;
    @FXML
    private TableColumn<?, ?> custIDCol;
    @FXML
    private TableColumn<?, ?> custNameCol;
    @FXML
    private TableColumn<?, ?> custPhoneCol;
    @FXML
    private TableColumn<?, ?> custPostalCodeCol;
    //************************************************
    // Menu toggle group for All, Month, Week
    //************************************************
    @FXML
    private ToggleGroup menuAppointmentType;
    @FXML
    private RadioButton menuAllAppointments;
    @FXML
    private RadioButton menuCurrentAppointments;
    @FXML
    private RadioButton menuMonthAppointments;
    //************************************************
    // Buttons
    //************************************************
    @FXML
    private Button menuApptAddBtn;
    @FXML
    private Button menuApptDeleteBtn;
    @FXML
    private Button menuApptUpdateBtn;
    @FXML
    private Button menuCustAddBtn;
    @FXML
    private Button menuCustDeleteBtn;
    @FXML
    private Button menuCustUpdateBtn;
    @FXML
    private Button menuLogoutBtn;
    @FXML
    private Button menuReportsBtn;
    //************************************************
    // Events
    //************************************************
    // Add appointment - No selector

    public static Customer chooseYourCustomer() {
        return chosenCustomer;
    }
    public static Appointment chooseYourAppointment() {
        return chosenAppointment;
    }

    @FXML
    void onMouseClicked(MouseEvent event) {
        menuPane.requestFocus();
    }

    @FXML
    void menuApptAddClick(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = new Scene(FXMLLoader.load(getClass().getResource("/DBClientApp/view/AddAppointment.fxml")));
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void menuApptDeleteClick(ActionEvent event) throws SQLException, IOException {
        chosenAppointment = menuApptTable.getSelectionModel().getSelectedItem();
        if (chosenAppointment == null) {
            alert(4);
        }
        else {
            alert(5);
            ObservableList<Appointment> apptList = AppointmentData.getAllApps();
            menuApptTable.setItems(apptList);
        }
    }

    @FXML
    void menuApptUpdateClick(ActionEvent event) throws IOException, SQLException {
        chosenAppointment = menuApptTable.getSelectionModel().getSelectedItem();
        if (chosenAppointment == null) {
            alert(4);
            return;
        }
        try {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = new Scene(FXMLLoader.load(getClass().getResource("/DBClientApp/view/UpdateAppointment.fxml")));
            stage.setTitle("Update Appointment");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        }
        catch (IOException e) {
            alert(4);
        }
    }

    @FXML
    void menuCustAddClick(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = new Scene(FXMLLoader.load(getClass().getResource("/DBClientApp/view/AddCustomer.fxml")));
        stage.setTitle("Add Customer Menu");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void menuCustDeleteClick(ActionEvent event) throws SQLException, IOException {
        chosenCustomer = menuCustTable.getSelectionModel().getSelectedItem();
        if (chosenCustomer == null) {
            alert(1);
        }
        else {
            ObservableList<Appointment> allAppts = AppointmentData.getAllApps();
            ObservableList<Appointment> custAppts = FXCollections.observableArrayList();
            for (Appointment a : allAppts) {
                if (chosenCustomer.getCustomerID() == a.getCustomerID()) {
                    custAppts.add(a);
                }
            }
            if (!custAppts.isEmpty()) {
                int appointmentCount = custAppts.size();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Customer Has " + appointmentCount + " Appointments");
                alert.setHeaderText("Delete " + chosenCustomer.getCustomerName() + "'s Appointment(s)?");
                StringBuilder deleteAppts = new StringBuilder("The customer has " + appointmentCount + " appointment(s) scheduled:\n\n");
                for (Appointment delApp : custAppts) {
                    deleteAppts.append("Appt. ID: ").append(delApp.getAppointmentID()).append(", of Appointment Type: ").append(delApp.getType()).append("\n");
                }
                alert.setContentText(deleteAppts.toString());
                Optional<ButtonType> decision = alert.showAndWait();
                if (decision.isPresent() && decision.get() == ButtonType.OK) {
                    AppointmentData.deleteAppt(chosenCustomer.getCustomerID());

                    Alert alertConf = new Alert(Alert.AlertType.INFORMATION);
                    alertConf.setTitle("Appointments Deleted");
                    alertConf.setHeaderText("The following appointments have been deleted:\n\n");
                    StringBuilder apptsDeleted = new StringBuilder();
                    for (Appointment delApp : custAppts) {
                        apptsDeleted.append("Appointment ID: " + delApp.getAppointmentID() + ", of type: " + delApp.getType() + "\n");
                    }
                    alertConf.setContentText(apptsDeleted.toString());
                    alertConf.showAndWait();
                    alert(2);
                    ObservableList<Appointment> apptList = AppointmentData.getAllApps();
                    menuApptTable.setItems(apptList);
                    ObservableList<Customer> custList = CustomerData.getAllCusts();
                    menuCustTable.setItems(custList);
                }
                else if (decision.isPresent() && decision.get() == ButtonType.CANCEL) {
                    return;
                }
            }
            else {
                alert(2);
                ObservableList<Customer> custList = CustomerData.getAllCusts();
                menuCustTable.setItems(custList);
            }
        }
    }

    @FXML
    void menuCustUpdateClick(ActionEvent event) throws IOException, SQLException {
        chosenCustomer = menuCustTable.getSelectionModel().getSelectedItem();
        if (chosenCustomer == null) {
            alert(1);
            return;
        }
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = new Scene(FXMLLoader.load(getClass().getResource("/DBClientApp/view/UpdateCustomer.fxml")));
        stage.setTitle("Update Customer Menu");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void menuLogoutClick(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = new Scene(FXMLLoader.load(getClass().getResource("/DBClientApp/view/Login.fxml")));
        stage.setTitle("Login Menu");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void menuRadioAll(ActionEvent event) {

    }

    @FXML
    void menuRadioCurrent(ActionEvent event) {

    }

    @FXML
    void menuRadioMonth(ActionEvent event) {

    }

    @FXML
    void menuReportClick(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = new Scene(FXMLLoader.load(getClass().getResource("/DBClientApp/view/Report.fxml")));
        stage.setTitle("Report Menu");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    private void alert(int alertNum) throws IOException, SQLException {
        switch (alertNum) {
            // Customer Alerts
            case 1 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("D'oh!");
                alert.setHeaderText("No Customer Selected");
                alert.setContentText("Choices are tough, but you must make one. " +
                        "Highlight a row on the Customer table and try again.");
                alert.showAndWait();
            }
            case 2 -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Customer Deletion");
                alert.setHeaderText("Are you sure?");
                alert.setContentText(chosenCustomer.getCustomerName() + " will be deleted if you continue!");
                Optional<ButtonType> decision = alert.showAndWait();
                if (decision.isPresent() && decision.get() == ButtonType.OK) {
                    String customer = chosenCustomer.getCustomerName();
                    CustomerData.delete(chosenCustomer.getCustomerID());
                    Alert alertY = new Alert(Alert.AlertType.INFORMATION);
                    alertY.setTitle("Customer Deletion");
                    alertY.setHeaderText("Now you've done it!");
                    alertY.setContentText(customer + " has been deleted from the database.");
                    alertY.showAndWait();
                }
            }
            case 3 -> {
                // Handle a customer that has appointments scheduled.
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Customer Deletion");
                alert.setHeaderText("Delete " + chosenCustomer.getCustomerName() + "'s Appointment(s)?");
                alert.setContentText("Click OK to delete the customer's appointments, or cancel to delete them manually.");
                Optional<ButtonType> decision = alert.showAndWait();
                if (decision.isPresent() && decision.get() == ButtonType.OK) {
                    AppointmentData.deleteAppt(chosenCustomer.getCustomerID());
                    alert(2);
                }
            }
            // Appointment Alerts
            case 4 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("D'oh!");
                alert.setHeaderText("No Appointment Selected");
                alert.setContentText("Choices are tough, but you must make one. " +
                        "Highlight a row on the Appointment table and try again.");
                alert.showAndWait();
            }
            case 5 -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Appointment Deletion");
                alert.setHeaderText("Are you sure?");
                alert.setContentText("Appointment " + chosenAppointment.getAppointmentID() + " of type " + chosenAppointment.getType() + " will be deleted if you continue!");
                Optional<ButtonType> decision = alert.showAndWait();
                if (decision.isPresent() && decision.get() == ButtonType.OK) {
                    int apptID = chosenAppointment.getAppointmentID();
                    AppointmentData.deleteOneAppt(apptID);
                    Alert alertZ = new Alert(Alert.AlertType.INFORMATION);
                    alertZ.setTitle("Appointment Deletion");
                    alertZ.setHeaderText("Now you've done it!");
                    alertZ.setContentText("Appointment ID: " + chosenAppointment.getAppointmentID() + ", of type: " + chosenAppointment.getType() + " has been deleted from the database.");
                    alertZ.showAndWait();
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Menu Controller Launched");
        //************************************************
        // Show the standard tables unfiltered
        //************************************************
        //Appointment Table
        ObservableList<Appointment> appsList = AppointmentData.getAllApps();
        menuApptTable.setItems(appsList);

        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptCustIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        apptUserIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));


        //************************************************
        // Customer Table
        ObservableList<Customer> custList = CustomerData.getAllCusts();
        menuCustTable.setItems(custList);

        custNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        custIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        custAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        custPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        custCountryCol.setCellValueFactory(new PropertyValueFactory<>("countryName"));
    }
}
