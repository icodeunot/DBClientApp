package DBClientApp.controller;

import DBClientApp.DAO.ContactData;
import DBClientApp.DAO.CustomerData;
import DBClientApp.DAO.UserData;
import DBClientApp.model.Appointment;
import DBClientApp.model.Contact;
import DBClientApp.model.Customer;
import DBClientApp.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class UpdateAppointmentController implements Initializable {

    Stage stage;
    Scene scene;
    static Appointment chosenAppointment;
    static Contact thisContact;

    // Get system timezone
    ZoneId userTime = ZoneId.systemDefault();
    // Get the times converted for combobox insertion
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
    private Pane updateApptPane;
    @FXML
    private TextField updateApptApptIDTxt;
    @FXML
    private Button updateApptCancelBtn;
    @FXML
    private ComboBox<Contact> updateApptContactCombo;
    @FXML
    private ComboBox<Integer> updateApptCustIDCombo;
    @FXML
    private TextField updateApptDescriptionTxt;
    @FXML
    private DatePicker updateApptEDateCal;
    @FXML
    private ComboBox<LocalTime> updateApptETimeCombo;
    @FXML
    private TextField updateApptLocationTxt;
    @FXML
    private DatePicker updateApptSDateCal;
    @FXML
    private ComboBox<LocalTime> updateApptSTimeCombo;
    @FXML
    private Button updateApptSaveBtn;
    @FXML
    private TextField updateApptTitleTxt;
    @FXML
    private TextField updateApptTypeTxt;
    @FXML
    private ComboBox<Integer> updateApptUserIDCombo;
    @FXML
    void onMouseClicked(MouseEvent event) {
        updateApptPane.requestFocus();
    }
    @FXML
    void updateApptApptIDClick(ActionEvent event) {
    }
    @FXML
    void updateApptCancelClick(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = new Scene(FXMLLoader.load(getClass().getResource("/DBClientApp/view/Menu.fxml")));
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
    @FXML
    void updateApptContactClick(ActionEvent event) {
    }
    @FXML
    void updateApptCustIDClick(ActionEvent event) {
    }
    @FXML
    void updateApptDescriptionClick(ActionEvent event) {
    }
    @FXML
    void updateApptEDateClick(ActionEvent event) {
        // get this method's instance of the conversion.
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
        // check for null date and then for the start date chosen. Can't be historical and if it starts today, the start time will have to be after the current time.

        if (updateApptEDateCal.getValue() != null) {
            if (updateApptEDateCal.getValue().isBefore(LocalDate.now())) {
                updateApptEDateCal.setValue(null);
                updateApptEDateCal.setPromptText("Select please...");
                updateApptETimeCombo.setValue(null);
                updateApptETimeCombo.setPromptText("Select please...");
                alert(1);
                updateApptPane.requestFocus();

            }
            else if (updateApptEDateCal.getValue() != null && updateApptEDateCal.getValue().equals(LocalDate.now()) && todayHourStart.isAfter(myHourEnd)) {
                updateApptEDateCal.setValue(null);
                updateApptEDateCal.setPromptText("Select please...");
                updateApptETimeCombo.setValue(null);
                updateApptETimeCombo.setPromptText("Select please...");
                alert(6);
                updateApptPane.requestFocus();
            }
            else if (updateApptEDateCal.getValue() != null && updateApptSDateCal.getValue() != null && updateApptSDateCal.getValue().isAfter(updateApptEDateCal.getValue())) {
                updateApptEDateCal.setValue(null);
                updateApptEDateCal.setPromptText("Select please...");
                updateApptETimeCombo.setValue(null);
                updateApptETimeCombo.setPromptText("Select please...");
                alert(3);
                updateApptPane.requestFocus();
            }
            // set the time combo box with a successful start date.
            else {
                if (updateApptEDateCal.getValue().equals(LocalDate.now()) && updateApptSDateCal.getValue() != null) {
                    updateApptETimeCombo.getItems().clear();
                    // updateApptSTimeCombo.setValue(null);
                    LocalTime endStart = todayHourStart.toLocalTime();
                    while (endStart.isBefore(myHourEnd.toLocalTime().minusMinutes(5).plusSeconds(1))) {
                        updateApptETimeCombo.getItems().add(LocalTime.from(endStart));
                        endStart = endStart.plusMinutes(5);
                    }
                }
                else {
                    updateApptETimeCombo.getItems().clear();
                    LocalTime endStart = myHourStart.toLocalTime();
                    while (endStart.isBefore(myHourEnd.toLocalTime().minusMinutes(5).plusSeconds(1))) {
                        updateApptETimeCombo.getItems().add(LocalTime.from(endStart));
                        endStart = endStart.plusMinutes(5);
                    }
                }
            }
            updateApptETimeCombo.setPromptText("Select please...");
        }

        // check for null date and then for the end date chosen. Can't be historical and if it starts today, the start time will have to be after the current time.
//        if ((updateApptSDateCal.getValue() != null && updateApptEDateCal.getValue() != null) && updateApptEDateCal.getValue().equals(LocalDate.now()) && todayHourStart.isAfter(myHourEnd)) {
//            updateApptETimeCombo.setValue(null);
//            alert(6);
//        }
//        else if ((updateApptSDateCal.getValue() != null && updateApptEDateCal.getValue() != null) && updateApptEDateCal.getValue().isBefore(LocalDate.now())) {
//            updateApptETimeCombo.getItems().clear();
//            updateApptETimeCombo.setValue(null);
//            alert(1);
//        }
//        else if ((updateApptSDateCal.getValue() != null && updateApptEDateCal.getValue() != null) && updateApptEDateCal.getValue().equals(LocalDate.now())) {
//            updateApptETimeCombo.getItems().clear();
//            LocalTime endStart = todayHourStart.toLocalTime().plusMinutes(5);
//            while (endStart.isBefore(myHourEnd.toLocalTime().plusSeconds(1))) {
//                updateApptETimeCombo.getItems().add(LocalTime.from(endStart));
//                endStart = endStart.plusMinutes(5);
//            }
//            // updateApptETimeCombo.setDisable(false);
//        }
//        else {
//            updateApptETimeCombo.getItems().clear();
//            LocalTime endStart = myHourStart.toLocalTime();
//            while (endStart.isBefore(myHourEnd.toLocalTime().plusSeconds(1))) {
//                updateApptETimeCombo.getItems().add(LocalTime.from(endStart));
//                endStart = endStart.plusMinutes(5);
//            }
//            // updateApptETimeCombo.setDisable(false);
//        }
//        updateApptEDateCal.setValue(updateApptSDateCal.getValue());
//    }
        //        LocalDate endDate = updateApptEDateCal.getValue();
//        LocalDate startDate = updateApptSDateCal.getValue();
//        if (endDate != null && startDate != null && endDate.isBefore(startDate)) {
//            updateApptEDateCal.setValue(updateApptSDateCal.getValue());
//            alert(3);
//        }
//        else if (endDate != null && startDate != null && endDate.isAfter(startDate)) {
//            LocalTime apptStart = myHourStart.toLocalTime();
//            while (apptStart.isBefore(myHourEnd.toLocalTime().plusSeconds(1))) {
//                updateApptETimeCombo.getItems().add(LocalTime.from(apptStart));
//                apptStart = apptStart.plusMinutes(5);
//            }
//            updateApptETimeCombo.setValue(null);
//        }
    }
    @FXML
    void updateApptETimeClick(ActionEvent event) {
    //    // need to check for updated time first
    //    if (updateApptSTimeCombo.getSelectionModel().getSelectedItem() != null) {
    //        if (updateApptEDateCal.getValue() != null && updateApptSDateCal.getValue() != null && updateApptEDateCal.getValue().isEqual(updateApptSDateCal.getValue())) {
    //            // End date before start date
    //            updateApptETimeCombo.getItems().clear();
    //            alert(3);
    //        } else if (updateApptEDateCal.getValue() != null && updateApptSDateCal.getValue() != null && updateApptEDateCal.getValue().isEqual(updateApptSDateCal.getValue())) {
    //            // Start/End Date the same
    //            updateApptETimeCombo.getItems().clear();
    //            LocalTime apptStart = updateApptSTimeCombo.getSelectionModel().getSelectedItem().plusMinutes(5);
    //            System.out.println(apptStart + ",- apptStart");
    //            while (apptStart.isBefore(myHourEnd.toLocalTime().plusSeconds(1))) {
    //                updateApptETimeCombo.getItems().add(LocalTime.from(apptStart));
    //                apptStart = apptStart.plusMinutes(5);
    //            }
    //        } else {
    //            // End Date after Start Date
    //            updateApptETimeCombo.getItems().clear();
    //            LocalTime apptStart = myHourStart.toLocalTime();
    //            while (apptStart.isBefore(myHourEnd.toLocalTime().plusSeconds(1))) {
    //                updateApptETimeCombo.getItems().add(LocalTime.from(apptStart));
    //                apptStart = apptStart.plusMinutes(5);
    //            }
    //        }
    //    }
    }
    @FXML
    void updateApptLocationClick(ActionEvent event) {
    }
    @FXML
    void updateApptSDateClick(ActionEvent event) {
        // get this method's instance of the conversion.
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
        // check for null date and then for the start date chosen. Can't be historical and if it starts today, the start time will have to be after the current time.

        if (updateApptSDateCal.getValue() != null) {
            if (updateApptSDateCal.getValue().isBefore(LocalDate.now())) {
                updateApptSDateCal.setValue(null);
                updateApptSDateCal.setPromptText("Select please...");
                updateApptSTimeCombo.setValue(null);
                updateApptSTimeCombo.setPromptText("Select please...");
                alert(1);
                updateApptPane.requestFocus();

            }
            else if (updateApptSDateCal.getValue() != null && updateApptSDateCal.getValue().equals(LocalDate.now()) && todayHourStart.isAfter(myHourEnd)) {
                updateApptSDateCal.setValue(null);
                updateApptSDateCal.setPromptText("Select please...");
                updateApptSTimeCombo.setValue(null);
                updateApptSTimeCombo.setPromptText("Select please...");
                alert(6);
                updateApptPane.requestFocus();
            }
            else if (updateApptEDateCal.getValue() != null && updateApptSDateCal.getValue().isAfter(updateApptEDateCal.getValue())) {
                updateApptSDateCal.setValue(null);
                updateApptSDateCal.setPromptText("Select please...");
                updateApptSTimeCombo.setValue(null);
                updateApptSTimeCombo.setPromptText("Select please...");
                alert(3);
                updateApptPane.requestFocus();
            }
            // set the time combo box with a successful start date.
            else {
                if (updateApptSDateCal.getValue().equals(LocalDate.now())) {
                    updateApptSTimeCombo.getItems().clear();
                    // updateApptSTimeCombo.setValue(null);
                    LocalTime endStart = todayHourStart.toLocalTime();
                    while (endStart.isBefore(myHourEnd.toLocalTime().minusMinutes(5).plusSeconds(1))) {
                        updateApptSTimeCombo.getItems().add(LocalTime.from(endStart));
                        endStart = endStart.plusMinutes(5);
                    }
                }
                else {
                    updateApptSTimeCombo.getItems().clear();
                    LocalTime endStart = myHourStart.toLocalTime();
                    while (endStart.isBefore(myHourEnd.toLocalTime().minusMinutes(5).plusSeconds(1))) {
                        updateApptSTimeCombo.getItems().add(LocalTime.from(endStart));
                        endStart = endStart.plusMinutes(5);
                    }
                }
            }
            updateApptSTimeCombo.setPromptText("Select please...");
        }
    }
    @FXML
    void updateApptSTimeClick(ActionEvent event) {
    //    LocalDate startDate = updateApptSDateCal.getValue();
    //    if (startDate != null && startDate.equals(LocalDate.now())) {
    //        updateApptSTimeCombo.getItems().clear();
    //        System.out.println("MAde it to first if");
    //        updateApptSTimeCombo.getItems().clear();
    //        LocalTime localOpen = LocalTime.now();
    //        int minute = localOpen.getMinute();
    //        int timeToAdd = 5 - (minute % 10); // How many minutes to the next 5 minute mark?
    //        LocalTime todayStartTime = localOpen.plusMinutes(timeToAdd);
    //        ZonedDateTime todayStart = ZonedDateTime.of(startDate, LocalTime.of(todayStartTime.getHour(), 0), ZoneId.of("America/New_York"));
    //        ZonedDateTime newHourStart = todayStart.withZoneSameInstant(userTime);
    //        while (newHourStart.isBefore(myHourEnd.minusMinutes(5).plusSeconds(1))) {
    //            updateApptSTimeCombo.getItems().add(LocalTime.from(newHourStart));
    //            newHourStart = newHourStart.plusMinutes(5);
    //        }
    //    }
    //    else if (startDate != null && updateApptEDateCal.getValue().isAfter(startDate)) {
    //        updateApptSTimeCombo.getItems().clear();
    //        LocalTime localOpen = myHourStart.toLocalTime();
    //        while(localOpen.isBefore(myHourEnd.toLocalTime().minusMinutes(5).plusSeconds(1))) {
    //            updateApptSTimeCombo.getItems().add(LocalTime.from(localOpen));
    //            localOpen = localOpen.plusMinutes(5);
    //        }
    //    }
    }
    @FXML
    void updateApptSaveClick(ActionEvent event) {
    }
    @FXML
    void updateApptTitleClick(ActionEvent event) {
    }
    @FXML
    void updateApptTypeClick(ActionEvent event) {
    }
    @FXML
    void updateApptUserIDClick(ActionEvent event) {
    }

    private void alert(int alertNum) {
        switch (alertNum) {
            case 1 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Great Scott!");
                alert.setHeaderText("There's a problem with the Flux Capacitor!\n(Can't pick a historical date)");
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
                alert.setHeaderText("Appointment Updated");
                alert.setContentText("Your appointment has been Updated in the database!");
                alert.showAndWait();
            }
            case 6 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("D'oh!");
                alert.setHeaderText("We're closed for the day");
                alert.setContentText("We've closed for the day, so you must start an appointment tomorrow.");
                alert.showAndWait();
            }
            case 7 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ruh-Roh");
                alert.setHeaderText("Looks like your appointment has trouble!");
                alert.setContentText("Some of your appointment details have already occurred. Please re-select appointment dates and/or times.");
                alert.showAndWait();
                updateApptSDateCal.setValue(null);
                updateApptSDateCal.setPromptText("Select please...");
                updateApptSTimeCombo.setValue(null);
                updateApptSTimeCombo.setPromptText("Select please...");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Update Appointment Controller Launched");
        // Data the controller will need
        chosenAppointment = MenuController.chooseYourAppointment();
        if (minute == 0) {
            minute = 0;
        }
        System.out.println("Minute -> " + minute);
        // Start Time Combo Box
        updateApptSTimeCombo.getItems().clear();
        LocalTime localOpen = myHourStart.toLocalTime();
        while(localOpen.isBefore(myHourEnd.toLocalTime().minusMinutes(5).plusSeconds(1))) {
            updateApptSTimeCombo.getItems().add(LocalTime.from(localOpen));
            localOpen = localOpen.plusMinutes(5);
        }
        // End Time Combo Box

        // Contact Names
        ObservableList<Contact> contacts = ContactData.getContact();
        for (Contact c : contacts) {
            if (chosenAppointment.getContact().equals(c.getContactName())) {
                thisContact = c;
            }
        }
        // Customer IDs
        ObservableList<Customer> customers = CustomerData.getAllCusts();
        ObservableList<Integer> customerIDs = FXCollections.observableArrayList();
        for (Customer c : customers) {
            int custID = c.getCustomerID();
            customerIDs.add(custID);
        }
        // User IDs
        ObservableList<User> users = UserData.getUsers();
        ObservableList<Integer> userIDs = FXCollections.observableArrayList();
        for (User u : users) {
            int userID = u.getUserID();
            userIDs.add(userID);
        }

        // get date and time ready for initial pull from appointment data
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startInfo = LocalDateTime.parse(chosenAppointment.getStart(), dateFormat);
        LocalDate startDate = startInfo.toLocalDate();
        LocalTime startTime = startInfo.toLocalTime();
        LocalDateTime endInfo = LocalDateTime.parse(chosenAppointment.getEnd(), dateFormat);
        LocalDate endDate = endInfo.toLocalDate();
        LocalTime endTime = endInfo.toLocalTime();
        // assign text values
        updateApptApptIDTxt.setText(String.valueOf(chosenAppointment.getAppointmentID()));
        updateApptTitleTxt.setText(chosenAppointment.getTitle());
        updateApptDescriptionTxt.setText(chosenAppointment.getDescription());
        updateApptLocationTxt.setText(chosenAppointment.getLocation());
        updateApptTypeTxt.setText(chosenAppointment.getType());
        // assign the dates and times
        updateApptSDateCal.setValue(startDate);
        updateApptEDateCal.setValue(endDate);
        updateApptSTimeCombo.setValue(startTime);
        // LocalTime apptStart = updateApptSTimeCombo.getSelectionModel().getSelectedItem().plusMinutes(5);
        // while (apptStart.isBefore(myHourEnd.toLocalTime().plusSeconds(1))) {
        //     updateApptETimeCombo.getItems().add(LocalTime.from(apptStart));
        //     apptStart = apptStart.plusMinutes(5);
        // }
        updateApptETimeCombo.setValue(endTime);
        // Create lists: Contacts, Customers, Users - assign original appt values.
        updateApptContactCombo.setItems(contacts);
        updateApptContactCombo.setValue(thisContact);
        updateApptCustIDCombo.setItems(customerIDs);
        updateApptCustIDCombo.setValue(chosenAppointment.getCustomerID());
        updateApptUserIDCombo.setItems(userIDs);
        updateApptUserIDCombo.setValue(chosenAppointment.getAppointmentID());

        if ((startDate.isBefore(LocalDate.now()) || (startDate.equals(LocalDate.now()) && (startTime.isBefore(LocalTime.from(todayHourStart)) || startTime.isAfter(LocalTime.from(myHourEnd))))
        || startDate.isAfter(endDate)) || (startDate.equals(endDate) && startTime.isAfter(updateApptETimeCombo.getSelectionModel().getSelectedItem()))) {
            alert(7);
            if (endDate.isBefore(LocalDate.now()) || (endDate.equals(LocalDate.now()) && endTime.isBefore(LocalTime.from(todayHourStart)))) {
                updateApptEDateCal.setValue(null);
                updateApptEDateCal.setPromptText("Select please...");
                updateApptETimeCombo.setValue(null);
                updateApptETimeCombo.setPromptText("Select please...");
            }
        }

        //else if (startDate.equals(LocalDate.now())) {
        //    if (startTime.isAfter(LocalTime.from(myHourEnd))) {
        //        alert(7);
        //    }
        //}
    }
}
