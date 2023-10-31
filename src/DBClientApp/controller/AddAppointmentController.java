package DBClientApp.controller;

import DBClientApp.DAO.*;
import DBClientApp.model.*;
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
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


/**
 * AddAppointmentController class. This class handles the logic for adding an appointment to the database.
 */
public class AddAppointmentController implements Initializable {

    // AddAppointmentController variables
    Stage stage;
    Scene scene;

    // Time data for general use of new inputs
    ZoneId userTime = ZoneId.systemDefault();
    ZonedDateTime est2systemStart = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8, 0), ZoneId.of("America/New_York"));
    ZonedDateTime myHourStart = est2systemStart.withZoneSameInstant(userTime);
    public int startMinute() {
        int minute = LocalTime.now().getMinute();
        minute = (minute >= 0 && minute <= 59) ? minute : 0;
        int getTimeToAdd = minute % 5;
        int startMin = minute + (getTimeToAdd > 0 ? 5 - getTimeToAdd : 0);
        startMin = (startMin >= 0 && startMin <= 59) ? startMin : 0;
        return startMin;
    }
    int hour = LocalTime.now().getHour();
    ZonedDateTime todayStartConversion = ZonedDateTime.of(LocalDate.now(), LocalTime.of(hour, startMinute()), ZoneId.of(String.valueOf(userTime)));
    ZonedDateTime todayHourStart = todayStartConversion.withZoneSameInstant(userTime);
    ZonedDateTime est2systemEnd = ZonedDateTime.of(LocalDate.now(), LocalTime.of(22, 0), ZoneId.of("America/New_York"));
    ZonedDateTime myHourEnd = est2systemEnd.withZoneSameInstant(userTime);

    @FXML private Pane addApptPane;
    @FXML private Button addApptCancelBtn;
    @FXML private Button addApptSaveBtn;
    @FXML private ComboBox<Contact> addApptContactCombo;
    @FXML private ComboBox<Integer> addApptCustIDCombo;
    @FXML private ComboBox<Integer> addApptUserIDCombo;
    @FXML private ComboBox<LocalTime> addApptETimeCombo;
    @FXML private ComboBox<LocalTime> addApptSTimeCombo;
    @FXML private DatePicker addApptSDateCal;
    @FXML private DatePicker addApptEDateCal;
    @FXML private TextField addApptDescriptionTxt;
    @FXML private TextField addApptLocationTxt;
    @FXML private TextField addApptTitleTxt;
    @FXML private TextField addApptTypeTxt;
    @FXML private TextField addApptApptIDTxt;

    /**
     * onMouseClicked. This event requests mouse focus if the user clicks off the text field. Adds visual assurance for the user.
      * @param event
     */
    @FXML void onMouseClicked(MouseEvent event) {
        addApptPane.requestFocus();
    }

    /**
     * addApptApptIDClick. This event calls the validateForm method to ensure input is valid.
     * @param event
     */
    @FXML void addApptApptIDClick(ActionEvent event) { validateForm(); }

    /**
     * addApptCancelClick. This event sends the user back to the main menu.
     * @param event
     * @throws IOException
     */
    @FXML void addApptCancelClick(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = new Scene(FXMLLoader.load(getClass().getResource("/DBClientApp/view/Menu.fxml")));
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * addApptContactClick. This event calls the validateForm method to ensure input is valid.
     * @param event
     */
    @FXML void addApptContactClick(ActionEvent event) {
        validateForm();
    }

    /**
     * addApptCustClick. This event calls the validateForm method to ensure input is valid.
     * @param event
     */
    @FXML void addApptCustIDClick(ActionEvent event) {
        validateForm();
    }

    /**
     * addApptDescriptionCLick. This event calls the validateForm method to ensure input is valid.
     * @param event
     */
    @FXML void addApptDescriptionClick(ActionEvent event) {
        validateForm();
    }

    /**
     * addApptEDateClick. This event calls the validateForm method to ensure input is valid.
     * @param event
     */
    @FXML void addApptEDateClick(ActionEvent event) {
        validateForm();
    }

    /**
     * addAPptETimeClick. This event calls the validateForm method to ensure input is valid.
     * @param event
     */
    @FXML void addApptETimeClick(ActionEvent event) {
        validateForm();
    }

    /**
     * addApptLocationClick. This event calls the validateForm method to ensure input is valid.
     * @param event
     */
    @FXML void addApptLocationClick(ActionEvent event) {
        validateForm();
    }

    /**
     * addApptSDateClick. This event will alert if the date is historical or the business has closed for the day.
     * The method sets the start time combo box with applicable times and sets the end date combo to the same date.
     * @param event
     */
    @FXML void addApptSDateClick(ActionEvent event) {
        addApptEDateCal.setValue(addApptSDateCal.getValue());
        addApptEDateCal.setEditable(false);
        addApptEDateCal.setOpacity(1);

        // Time details for this instance
        int minute = LocalTime.now().getMinute();
        if (minute == 0) {
            minute = 0;
        }
        int getTimeToAdd = minute % 5;
        int hour = LocalTime.now().getHour();
        int startMin;
        startMin = minute + (getTimeToAdd > 0 ? 5 - getTimeToAdd : 0);
        if (startMin >= 60) {
            startMin = 0;
        }
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
            if (addApptSDateCal.getValue() != null) {
                if (addApptSDateCal.getValue().equals(LocalDate.now())) {
                    if (todayHourStart.isAfter(myHourEnd)) {
                        LocalTime localOpen = myHourStart.toLocalTime();
                        while (localOpen.isBefore(myHourEnd.toLocalTime().minusMinutes(5).plusSeconds(1))) {
                            addApptSTimeCombo.getItems().add(LocalTime.from(localOpen));
                            localOpen = localOpen.plusMinutes(5);
                        }
                    } else if (todayHourStart.isBefore(myHourStart)) {
                        LocalTime localOpen = myHourStart.toLocalTime();
                        while (localOpen.isBefore(myHourEnd.toLocalTime().plusSeconds(1))) {
                            addApptSTimeCombo.getItems().add(LocalTime.from(localOpen));
                            localOpen = localOpen.plusMinutes(5);
                        }
                    } else {
                        addApptSTimeCombo.getItems().clear();
                        LocalTime localOpen = todayHourStart.toLocalTime();
                        while (localOpen.isBefore(myHourEnd.toLocalTime().minusMinutes(5).plusSeconds(1))) {
                            addApptSTimeCombo.getItems().add(LocalTime.from(localOpen));
                            localOpen = localOpen.plusMinutes(5);
                        }
                    }
                }
            } else {
                LocalTime localOpen = myHourStart.toLocalTime();
                while (localOpen.isBefore(myHourEnd.toLocalTime().minusMinutes(5).plusSeconds(1))) {
                    addApptSTimeCombo.getItems().add(LocalTime.from(localOpen));
                    localOpen = localOpen.plusMinutes(5);
                }
            }
        }
        validateForm();
    }

    /**
     * addApptSTimeClick. This event sets the start combo box based on various factors. An appt on the same day only allows
     * appointments to start after the current time. Choosing a start time will automatically set the end time combo box for
     * 5 minutes after the start time, through to closing.
     * @param event
     */
    @FXML void addApptSTimeClick(ActionEvent event) {
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

    /**
     * addApptSaveClick. This event verifies data and saves the info to the database.
     * @param event
     */
    @FXML void addApptSaveClick(ActionEvent event) {
        ObservableList<Appointment> apptList = AppointmentData.getAllApps();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String thisStart = addApptSDateCal.getValue() + " " + addApptSTimeCombo.getSelectionModel().getSelectedItem();
        String thisEnd = addApptEDateCal.getValue() + " " + addApptETimeCombo.getSelectionModel().getSelectedItem();
        LocalDateTime thisStartDateTime = LocalDateTime.parse(thisStart, dateFormat);
        LocalDateTime thisEndDateTime = LocalDateTime.parse(thisEnd, dateFormat);

        ObservableList<Appointment> customerAppts = FXCollections.observableArrayList();
        for (Appointment a : apptList) {
            if (a.getCustomerID() == addApptCustIDCombo.getSelectionModel().getSelectedItem()) {
                customerAppts.add(a);
            }
            for (Appointment appt : customerAppts) {
                LocalDateTime custStarts = LocalDateTime.parse(appt.getStart(), dateFormat);
                LocalDateTime custEnds = LocalDateTime.parse(appt.getEnd(), dateFormat);

                if (thisStartDateTime.equals(custStarts)) {
                    alert(7);
                    return;
                }
                else if (thisStartDateTime.isAfter(custStarts) && thisStartDateTime.isBefore(custEnds)) {
                    alert(7);
                    return;
                }
                else if (thisStartDateTime.isBefore(custStarts) && thisEndDateTime.isAfter(custStarts)) {
                    alert(7);
                    return;
                }
                else if (thisStartDateTime.isAfter(custStarts) && thisEndDateTime.isBefore(custEnds)) {
                    alert(7);
                    return;
                }
            }
        }

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

    /**
     * addApptTitleClick. This event calls the validateForm method to ensure input is valid.
     * @param event
     */
    @FXML void addApptTitleClick(ActionEvent event) {
        validateForm();
    }

    /**
     * addApptTypeClick. This event calls the validateForm method to ensure input is valid.
     * @param event
     */
    @FXML void addApptTypeClick(ActionEvent event) {
        validateForm();
    }

    /**
     * addApptUserIDClick. This event calls the validateForm method to ensure input is valid.
     * @param event
     */
    @FXML void addApptUserIDClick(ActionEvent event) {
        validateForm();
    }

    /**
     * alert. This method communicates various errors and information to the user.
     * @param alertNum
     */
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
            case 7 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("D'oh!");
                alert.setHeaderText("So this is what it's like when appointments collide.");
                alert.setContentText("Your chosen appointment time overlaps with another appointment for this customer. Please pick another time.");
                alert.showAndWait();
            }
        }
    }

    /**
     * validateForm. This method ensures all data is filled before allowing the appointment to be added.
     */
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

    /**
     * initialize. This method initializes the addAppointment page. It applies date and time logic to the combo boxes.
     * The method creates lists for the users, contacts, and customers.
     * The method starts the save button as disabled in order to verify logic above with the validateForm calls.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Add Appointment Controller Launched");
        addApptSaveBtn.setDisable(true);

        // Time details for initialization
        ZoneId userTime = ZoneId.systemDefault();
        ZonedDateTime est2systemStart = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8, 0), ZoneId.of("America/New_York"));
        ZonedDateTime myHourStart = est2systemStart.withZoneSameInstant(userTime);

        int minute = LocalTime.now().getMinute();
        minute = (minute >= 0 && minute <= 59) ? minute : 0;
        int getTimeToAdd = minute % 5;
        int hour = LocalTime.now().getHour();
        int startMin = minute + (getTimeToAdd > 0 ? 5 - getTimeToAdd : 0);
        startMin = (startMin >= 0 && startMin <= 59) ? startMin : 0;
        ZonedDateTime todayStartConversion = ZonedDateTime.of(LocalDate.now(), LocalTime.of(hour, startMin), ZoneId.of(String.valueOf(userTime)));
        ZonedDateTime todayHourStart = todayStartConversion.withZoneSameInstant(userTime);
        ZonedDateTime est2systemEnd = ZonedDateTime.of(LocalDate.now(), LocalTime.of(22, 0), ZoneId.of("America/New_York"));
        ZonedDateTime myHourEnd = est2systemEnd.withZoneSameInstant(userTime);

        // Disable the end date and have it auto-set
        addApptEDateCal.setDisable(true);
        addApptEDateCal.setStyle("-fx-opacity: 1;");

        LocalDate startDate = addApptSDateCal.getValue();
            if (startDate != null) {
                if (startDate.equals(LocalDate.now())) {
                    if (todayHourStart.isAfter(myHourEnd)) {
                        LocalTime localOpen = myHourStart.toLocalTime();
                        while (localOpen.isBefore(myHourEnd.toLocalTime().minusMinutes(5).plusSeconds(1))) {
                            addApptSTimeCombo.getItems().add(LocalTime.from(localOpen));
                            localOpen = localOpen.plusMinutes(5);
                        }
                    } else if (todayHourStart.isBefore(myHourStart)) {
                        LocalTime localOpen = myHourStart.toLocalTime();
                        while (localOpen.isBefore(myHourEnd.toLocalTime().plusSeconds(1))) {
                            addApptSTimeCombo.getItems().add(LocalTime.from(localOpen));
                            localOpen = localOpen.plusMinutes(5);
                        }
                    } else {
                        addApptSTimeCombo.getItems().clear();
                        LocalTime localOpen = todayHourStart.toLocalTime();
                        while (localOpen.isBefore(myHourEnd.toLocalTime().minusMinutes(5).plusSeconds(1))) {
                            addApptSTimeCombo.getItems().add(LocalTime.from(localOpen));
                            localOpen = localOpen.plusMinutes(5);
                        }
                    }
                }
            }
            else {
                LocalTime localOpen = myHourStart.toLocalTime();
                while (localOpen.isBefore(myHourEnd.toLocalTime().minusMinutes(5).plusSeconds(1))) {
                    addApptSTimeCombo.getItems().add(LocalTime.from(localOpen));
                    localOpen = localOpen.plusMinutes(5);
                }
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
