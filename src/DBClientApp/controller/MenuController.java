package DBClientApp.controller;

import DBClientApp.DAO.AppointmentData;
import DBClientApp.DAO.CustomerData;
import DBClientApp.DAO.UserData;
import DBClientApp.model.Appointment;
import DBClientApp.model.Customer;
import DBClientApp.model.User;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * MenuController class. This class handles the Main Menu logic.
 */
public class MenuController implements Initializable {

    // MenuController Attributes
    Stage stage;
    Scene scene;
    static Customer chosenCustomer;
    static Appointment chosenAppointment;
    private String userName;

    @FXML private Pane menuPane;
    @FXML private Button menuApptAddBtn;
    @FXML private Button menuApptDeleteBtn;
    @FXML private Button menuApptUpdateBtn;
    @FXML private Button menuCustAddBtn;
    @FXML private Button menuCustDeleteBtn;
    @FXML private Button menuCustUpdateBtn;
    @FXML private Button menuLogoutBtn;
    @FXML private Button menuReportsBtn;
    @FXML private ToggleGroup menuAppointmentType;
    @FXML private RadioButton menuAllAppointments;
    @FXML private RadioButton menuWeekAppointments;
    @FXML private RadioButton menuMonthAppointments;
    @FXML private TableColumn<?, ?> apptContactCol;
    @FXML private TableColumn<?, ?> apptCustIDCol;
    @FXML private TableColumn<?, ?> apptDescriptionCol;
    @FXML private TableColumn<?, ?> apptEndCol;
    @FXML private TableColumn<?, ?> apptIDCol;
    @FXML private TableColumn<?, ?> apptLocationCol;
    @FXML private TableColumn<?, ?> apptStartCol;
    @FXML private TableColumn<?, ?> apptTitleCol;
    @FXML private TableColumn<?, ?> apptTypeCol;
    @FXML private TableColumn<?, ?> apptUserIDCol;
    @FXML private TableColumn<?, ?> custAddressCol;
    @FXML private TableColumn<?, ?> custCountryCol;
    @FXML private TableColumn<?, ?> custIDCol;
    @FXML private TableColumn<?, ?> custNameCol;
    @FXML private TableColumn<?, ?> custPhoneCol;
    @FXML private TableColumn<?, ?> custPostalCodeCol;
    @FXML private TableView<Appointment> menuApptTable;
    @FXML private TableView<Customer> menuCustTable;

    /**
     * chooseYourCustomer. This event returns the customer object.
     * @return chosenCustomer
     */
    public static Customer chooseYourCustomer() {
        return chosenCustomer;
    }

    /**
     * chooseYourAppointment. This method returns the chosenAppointment.
     * @return chooseYourAppointment
     */
    public static Appointment chooseYourAppointment() {
        return chosenAppointment;
    }

    /**
     * onMouseClicked. This method requests focus of the mouse for user's visual assurance when clicking out of a text field.
     * @param event
     */
    @FXML void onMouseClicked(MouseEvent event) {
        menuPane.requestFocus();
    }

    /**
     * menuApptAddClick. This event sends the user to the Add Appointment form.
     * @param event
     * @throws IOException
     */
    @FXML void menuApptAddClick(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = new Scene(FXMLLoader.load(getClass().getResource("/DBClientApp/view/AddAppointment.fxml")));
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * menuApptDeleteClick. This event deletes an appointment if it passes logic.
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML void menuApptDeleteClick(ActionEvent event) throws SQLException, IOException {
        chosenAppointment = menuApptTable.getSelectionModel().getSelectedItem();
        if (chosenAppointment == null) {
            alert(4);
        }
        else {
            alert(5);
            ObservableList<Appointment> apptList = AppointmentData.getAllApps();
            menuApptTable.setItems(apptList);
        }
        menuAppointmentType.selectToggle(menuAllAppointments);
    }

    /**
     * menuApptUpdateClick. This event checks to make sure an appointment is selcted, and sends the user to the update appointment form.
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML void menuApptUpdateClick(ActionEvent event) throws IOException, SQLException {
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

    /**
     * menuCustAddClick. This method brings the user to the Add Customer form.
     * @param event
     * @throws IOException
     */
    @FXML void menuCustAddClick(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = new Scene(FXMLLoader.load(getClass().getResource("/DBClientApp/view/AddCustomer.fxml")));
        stage.setTitle("Add Customer Menu");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * menuCustDeleteClick. This event will delete a customer upon the users confirmation.
     * The method first checks for appointments tied to the customer and deletes them prior to deleting the customer.
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML void menuCustDeleteClick(ActionEvent event) throws SQLException, IOException {
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

    /**
     * menuCustUpdateClick. This event selects a customer based on the users choice from the tableview.
     * If there is no selection of a customer an error will fire. Otherwise the customer object is used in the update form.
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML void menuCustUpdateClick(ActionEvent event) throws IOException, SQLException {
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

    /**
     * menuLogoutClick. This event sends the user back to the Login Menu, effectively logging out the user.
     * The login page will require a password to return to the menu page.
     * @param event
     * @throws IOException
     */
    @FXML void menuLogoutClick(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = new Scene(FXMLLoader.load(getClass().getResource("/DBClientApp/view/Login.fxml")));
        stage.setTitle("Login Menu");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * menuRadioAll. This event is the All appointments radio button.
     * It fills the appointments tableview with all appointments.
     * @param event
     */
    @FXML void menuRadioAll(ActionEvent event) {
        if (menuAllAppointments.isSelected()) {
            menuApptTable.getItems().clear();
            ObservableList<Appointment> appsList = AppointmentData.getAllApps();
            menuApptTable.setItems(appsList);
        }
    }

    /**
     * menuRadioWeek. This event is the radio button for weekly appointments.
     * The code fills the appointment tableview with appointments within the current week.
     * @param event
     */
    @FXML void menuRadioWeek(ActionEvent event) {
        if (menuWeekAppointments.isSelected()) {
            menuApptTable.getItems().clear();
            menuApptTable.setPlaceholder(new Label("There are no appointments in the current week."));
            ObservableList<Appointment> appsList = AppointmentData.getWeekApps();
            menuApptTable.setItems(appsList);
        }
    }

    /**
     * menuRadioMonth. This event is the radio button for appointments by month.
     * The code fills the appointment tableview with appointments within the current month.
     * @param event
     */
    @FXML void menuRadioMonth(ActionEvent event) {
        if (menuMonthAppointments.isSelected()) {
            menuApptTable.getItems().clear();
            menuApptTable.setPlaceholder(new Label("There are no appointments in the current month."));
            ObservableList<Appointment> appsList = AppointmentData.getMonthApps();
            menuApptTable.setItems(appsList);
        }
    }

    /**
     * menuReportClick. This method brings the user to the Reports page.
     * @param event
     * @throws IOException
     */
    @FXML void menuReportClick(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = new Scene(FXMLLoader.load(getClass().getResource("/DBClientApp/view/Report.fxml")));
        stage.setTitle("Report Menu");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * alert. This method is used to communicate various errors and information to the user.
      * @param alertNum
     * @throws IOException
     * @throws SQLException
     */
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

    /**
     * initialize. This overridden initilaize method starts the menu controller and alerts for appointments within q5 minutes for the logged in user.
     * The method uses several calls to the database to fill the tableviews with the customers and appointments.
     * The alerts included in the method are used for the user appointment reminders.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Menu Controller Launched");
        ObservableList<User> userList = UserData.getUsers();
        ObservableList<User> thisUser = FXCollections.observableArrayList();
        int userID = -1;
        userName = LoginController.getUserName();
        int apptID = -1;
        StringBuilder start = new StringBuilder();
        for (User u : userList) {
            if (u.getUserName().equals(userName)) {
                thisUser.add(u);
                userID = u.getUserID();
            }
        }

        ObservableList<Appointment> appsList = AppointmentData.getAllApps();
        ObservableList<Appointment> userApps = FXCollections.observableArrayList();
        for (Appointment a : appsList) {
            if (a.getUserID() == userID) {
                userApps.add(a);
            }
        }

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime userTime = LocalDateTime.now();
        boolean hasAppt = false;
        for (Appointment alertAppt : userApps) {
            LocalDateTime thisStartDateTime = LocalDateTime.parse(alertAppt.getStart(), dateFormat);
            LocalDateTime thisEndDateTime = LocalDateTime.parse(alertAppt.getEnd(), dateFormat);
            if (thisStartDateTime.isBefore(userTime.plusMinutes(15)) && thisStartDateTime.isAfter(userTime.minusMinutes(15))) {
                hasAppt = true;
                apptID = alertAppt.getAppointmentID();
                start = new StringBuilder(start + "Appt ID: " + apptID + " at " + alertAppt.getStart() + "\n");
            }
        }
        if (hasAppt == true) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Get Ready!");
            alert.setHeaderText(userName + " has the following appointment(s) scheduled:\n" + start);
            alert.setContentText("Please check the schedule and get ready for your appointment(s).");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Woo Hoo!");
            alert.setHeaderText("No upcoming appointments!");
            alert.setContentText("Take a few minutes to relax, "+ userName +" has no upcoming appointments.");
            alert.showAndWait();
        }

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
