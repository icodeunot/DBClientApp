package DBClientApp.controller;

import DBClientApp.DAO.*;
import DBClientApp.model.Contact;
import DBClientApp.model.Customer;
import DBClientApp.model.User;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {

    Stage stage;
    Scene scene;

     // Get the times converted for combobox insertion
    // Get system timezone
    ZoneId userTime = ZoneId.systemDefault();

    // Start
    ZonedDateTime est2systemStart = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8, 0), ZoneId.of("America/New_York"));
    ZonedDateTime myHourStart = est2systemStart.withZoneSameInstant(userTime);

    // Time block to handle cases where an appointment begins today.
    int minute = LocalTime.now().getMinute();
    int getTimeToAdd = minute % 5;
    int hour = LocalTime.now().getHour();
    int startMin = minute + (getTimeToAdd > 0 ? 5 - getTimeToAdd : 0);
    ZonedDateTime todayStartConversion = ZonedDateTime.of(LocalDate.now(), LocalTime.of(hour, startMin), ZoneId.of(String.valueOf(userTime)));
    ZonedDateTime todayHourStart = todayStartConversion.withZoneSameInstant(userTime);

    // End
    ZonedDateTime est2systemEnd = ZonedDateTime.of(LocalDate.now(), LocalTime.of(22, 0), ZoneId.of("America/New_York"));
    ZonedDateTime myHourEnd = est2systemEnd.withZoneSameInstant(userTime);



    @FXML
    private Pane addApptPane;
    @FXML
    private TextField addApptApptIDTxt;
    @FXML
    private Button addApptCancelBtn;
    @FXML
    private ComboBox<Contact> addApptContactCombo;
    @FXML
    private ComboBox<Integer> addApptCustIDCombo;
    @FXML
    private TextField addApptDescriptionTxt;
    @FXML
    private DatePicker addApptEDateCal;
    @FXML
    private ComboBox<LocalTime> addApptETimeCombo;
    @FXML
    private TextField addApptLocationTxt;
    @FXML
    private DatePicker addApptSDateCal;
    @FXML
    private ComboBox<LocalTime> addApptSTimeCombo;
    @FXML
    private Button addApptSaveBtn;
    @FXML
    private TextField addApptTitleTxt;
    @FXML
    private TextField addApptTypeTxt;
    @FXML
    private ComboBox<Integer> addApptUserIDCombo;

    @FXML
    void onMouseClicked(MouseEvent event) {
        addApptPane.requestFocus();
    }
    @FXML
    void addApptApptIDClick(ActionEvent event) {
    }
    @FXML
    void addApptCancelClick(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = new Scene(FXMLLoader.load(getClass().getResource("/DBClientApp/view/Menu.fxml")));
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
    @FXML
    void addApptContactClick(ActionEvent event) {
        validateForm();
    }
    @FXML
    void addApptCustIDClick(ActionEvent event) {
        validateForm();
    }
    @FXML
    void addApptDescriptionClick(ActionEvent event) {
        validateForm();
    }
    @FXML
    void addApptEDateClick(ActionEvent event) {
        validateForm();
    }
    @FXML
    void addApptETimeClick(ActionEvent event) {
        validateForm();
    }
    @FXML
    void addApptLocationClick(ActionEvent event) {
        validateForm();
    }
    @FXML
    void addApptSDateClick(ActionEvent event) {
        // this method's local time needs
        int minute = LocalTime.now().getMinute();
        if (minute == 0) {
            minute = 0;
        }
        int getTimeToAdd = minute % 5;
        int hour = LocalTime.now().getHour();
        int startMin;

        startMin = minute + (getTimeToAdd > 0 ? 5 - getTimeToAdd : 0);

        ZonedDateTime todayStartConversion = ZonedDateTime.of(LocalDate.now(), LocalTime.of(hour, startMin), ZoneId.of(String.valueOf(userTime)));
        ZonedDateTime todayHourStart = todayStartConversion.withZoneSameInstant(userTime);
        // checking different date scenarios. keeping user from hurting themselves.
        if (addApptSDateCal.getValue() != null && addApptSDateCal.getValue().equals(LocalDate.now()) && todayHourStart.isAfter(myHourEnd)) {
            addApptSDateCal.getEditor().clear();
            addApptEDateCal.setValue(null);
            addApptSTimeCombo.setValue(null);
            addApptETimeCombo.setDisable(true);
            addApptPane.requestFocus();
            alert(6);
        }
        else if (addApptSDateCal.getValue() != null && addApptSDateCal.getValue().isBefore(LocalDate.now())) {
            addApptSDateCal.setValue(null);
            addApptEDateCal.setValue(addApptSDateCal.getValue());
            addApptSTimeCombo.setValue(null);
            addApptETimeCombo.setDisable(true);
            addApptPane.requestFocus();
            alert(1);
        }
        else if (addApptSDateCal.getValue() != null && addApptSDateCal.getValue().equals(LocalDate.now())) {
            addApptSTimeCombo.getItems().clear();
            LocalTime endStart = todayHourStart.toLocalTime();
            while (endStart.isBefore(myHourEnd.toLocalTime().minusMinutes(5).plusSeconds(1))) {
                addApptSTimeCombo.getItems().add(LocalTime.from(endStart));
                endStart = endStart.plusMinutes(5);
            }
            addApptEDateCal.setValue(addApptSDateCal.getValue());
            addApptETimeCombo.setDisable(true);
        }
        else {
            addApptSTimeCombo.getItems().clear();
            LocalTime endStart = myHourStart.toLocalTime();
            while (endStart.isBefore(myHourEnd.toLocalTime().minusMinutes(5).plusSeconds(1))) {
                addApptSTimeCombo.getItems().add(LocalTime.from(endStart));
                endStart = endStart.plusMinutes(5);
            }
            addApptEDateCal.setValue(addApptSDateCal.getValue());
            addApptETimeCombo.setDisable(true);
        }
        validateForm();
    }
    @FXML
    void addApptSTimeClick(ActionEvent event) {
        if (addApptSDateCal.getValue() != null) {
            addApptETimeCombo.setDisable(false);
            addApptETimeCombo.getItems().clear();
            if (addApptSTimeCombo.getSelectionModel().getSelectedItem() != null) {
                LocalTime endStart = addApptSTimeCombo.getSelectionModel().getSelectedItem().plusMinutes(5);
                while (endStart.isBefore(myHourEnd.toLocalTime().plusSeconds(1))) {
                    addApptETimeCombo.getItems().add(LocalTime.from(endStart));
                    endStart = endStart.plusMinutes(5);
                }
            } else {
                LocalTime localOpen = myHourStart.toLocalTime().plusMinutes(5);
                while (localOpen.isBefore(myHourEnd.toLocalTime().plusSeconds(1))) {
                    addApptETimeCombo.getItems().add(LocalTime.from(localOpen));
                    localOpen = localOpen.plusMinutes(5);
                }
            }
        }
        validateForm();
    }
    @FXML
    void addApptSaveClick(ActionEvent event) {
       try {
           String title = addApptTitleTxt.getText();
           String description = addApptDescriptionTxt.getText();
           String location = addApptLocationTxt.getText();
           String type = addApptTypeTxt.getText();
           // make a timestamp from the selections
           LocalDateTime apptStartDate = LocalDateTime.of(addApptSDateCal.getValue(), addApptSTimeCombo.getSelectionModel().getSelectedItem());
           LocalDateTime apptEndDate = LocalDateTime.of(addApptEDateCal.getValue(), addApptETimeCombo.getSelectionModel().getSelectedItem());
           Timestamp startTS = Timestamp.valueOf(apptStartDate);
           Timestamp endTS = Timestamp.valueOf(apptEndDate);
           int custID = addApptCustIDCombo.getSelectionModel().getSelectedItem();
           int userID = addApptUserIDCombo.getSelectionModel().getSelectedItem();
           int contactID = addApptContactCombo.getSelectionModel().getSelectedItem().getContactID();
           Timestamp emptyTS = Timestamp.valueOf("1970-01-01 00:00:00");
           if (title.isEmpty() || description.isEmpty() || location.isEmpty() || type.isEmpty() || startTS.equals(emptyTS) ||
               endTS.equals(emptyTS)) {
               alert(4);
           }
           else {
               AppointmentData.insertAppointment(title, description, location, type, startTS, endTS, custID, userID, contactID);
               alert(5);
               stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
               scene = new Scene(FXMLLoader.load(getClass().getResource("/DBClientApp/view/Menu.fxml")));
               stage.setTitle("Main Menu");
               stage.setScene(scene);
               stage.centerOnScreen();
               stage.show();
           }
       }
       catch (Exception e) {
           e.printStackTrace();
       }
    }
    @FXML
    void addApptTitleClick(ActionEvent event) {
        validateForm();
    }
    @FXML
    void addApptTypeClick(ActionEvent event) {
        validateForm();
    }
    @FXML
    void addApptUserIDClick(ActionEvent event) {
        validateForm();
    }
    private void alert(int alertNum) {
        switch (alertNum) {
            case 1 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Great Scott!");
                alert.setHeaderText("There's a problem with the Flux Capacitor!");
                alert.setContentText("If you don't choose today, you must go... into THE FUTURE!\n(choose today or later)");
                alert.showAndWait();
            }
            case 2 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("D'oh!");
                alert.setHeaderText("Let's start at the beginning");
                alert.setContentText("Please first choose a start date");
                alert.showAndWait();
            }
            case 3 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("D'oh!");
                alert.setHeaderText("You're trying to end before we begin.");
                alert.setContentText("Please choose an end date on, or after, the start date.");
                alert.showAndWait();
            }
            case 4 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("D'oh!");
                alert.setHeaderText("Invalid Appointment Data");
                alert.setContentText("Please fill out all data rows and re-submit");
                alert.showAndWait();
            }
            case 5 -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setHeaderText("Appointment Saved");
                alert.setContentText("Your appointment has been saved to the database!");
                alert.showAndWait();
            }
            case 6 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("D'oh!");
                alert.setHeaderText("We're closed for the day");
                alert.setContentText("We've closed for the day, so you must start an appointment tomorrow.");
                alert.showAndWait();
            }
        }
    }
    // Don't allow Save unless all fields have data
    private void validateForm() {
        boolean formValidated = true;
        if (addApptTitleTxt.getText().isEmpty() ||
            addApptDescriptionTxt.getText().isEmpty() ||
            addApptLocationTxt.getText().isEmpty() ||
            addApptTypeTxt.getText().isEmpty() ||
            addApptSDateCal.getValue() == null ||
            addApptEDateCal.getValue() == null ||
            addApptSTimeCombo.getSelectionModel().isEmpty() ||
            addApptETimeCombo.getSelectionModel().isEmpty() ||
            addApptContactCombo.getSelectionModel().isEmpty() ||
            addApptCustIDCombo.getSelectionModel().isEmpty() ||
            addApptUserIDCombo.getSelectionModel().isEmpty()) {
            formValidated = false;
        }
        addApptSaveBtn.setDisable(!formValidated);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Add Appointment Controller Launched");
        // validateForm();
        addApptSaveBtn.setDisable(true);
        if (minute == 0) {
            minute = 0;
        }
        // Disable the end date and have it auto-set
        addApptEDateCal.setDisable(true);
        addApptEDateCal.setStyle("-fx-opacity: 1;");

        // Fill the Start Time combobox
        LocalTime localOpen = myHourStart.toLocalTime();
        while(localOpen.isBefore(myHourEnd.toLocalTime().minusMinutes(5).plusSeconds(1))) {
            addApptSTimeCombo.getItems().add(LocalTime.from(localOpen));
            localOpen = localOpen.plusMinutes(5);
        }

        // Start the ETime combobox as disabled
        addApptETimeCombo.setDisable(true);

        // Fill the contact combo box
        ObservableList<Contact> contacts = ContactData.getContact();
        addApptContactCombo.setItems(contacts);

        // Fill the customerID combo box
        ObservableList<Customer> customers = CustomerData.getAllCusts();
        ObservableList<Integer> custIDs = FXCollections.observableArrayList();
        for (Customer c : customers) {
            Integer custID = c.getCustomerID();
            custIDs.add(custID);
        }
        addApptCustIDCombo.setItems(custIDs);

        // Fill the userID combo box
        ObservableList<User> users = UserData.getUsers();
        ObservableList<Integer> userIDs = FXCollections.observableArrayList();
        for (User u : users) {
            Integer userID = u.getUserID();
            userIDs.add(userID);
        }
        addApptUserIDCombo.setItems(userIDs);
    }
}
