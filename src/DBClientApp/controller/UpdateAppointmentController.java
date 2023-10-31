package DBClientApp.controller;

import DBClientApp.DAO.AppointmentData;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


/**
 * UpdateAppointmentController class. This class handles the logic behind updating a customer. Methods of note are validateForm(),
 * and the time instance sections. I would like to eventually turn that into a class for cleaner controllers.
 */
public class UpdateAppointmentController implements Initializable {

    // UpdateAppointmentController variables
    Stage stage;
    Scene scene;
    // Appointment object from the main menu selection
    static Appointment chosenAppointment;
    // Contact object
    static Contact thisContact;

    // Time variables
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

    @FXML private Pane updateApptPane;
    @FXML private Button updateApptCancelBtn;
    @FXML private Button updateApptSaveBtn;
    @FXML private ComboBox<Contact> updateApptContactCombo;
    @FXML private ComboBox<Integer> updateApptCustIDCombo;
    @FXML private ComboBox<Integer> updateApptUserIDCombo;
    @FXML private ComboBox<LocalTime> updateApptETimeCombo;
    @FXML private ComboBox<LocalTime> updateApptSTimeCombo;
    @FXML private DatePicker updateApptEDateCal;
    @FXML private DatePicker updateApptSDateCal;
    @FXML private TextField updateApptApptIDTxt;
    @FXML private TextField updateApptDescriptionTxt;
    @FXML private TextField updateApptLocationTxt;
    @FXML private TextField updateApptTitleTxt;
    @FXML private TextField updateApptTypeTxt;

    /**
     * onMouseClicked. This event requests focus of the mouse any time a user clicks out of the field. Mainly used for visual assuredness for the user.
      * @param event
     */
    @FXML void onMouseClicked(MouseEvent event) {
        updateApptPane.requestFocus();
    }

    /**
     * onMouseMoved. This event will call validateForm() anytime the user moves the mouse. This allows the save button
     * to become enabled with changes.
     * @param event
     */
    @FXML void onMouseMoved(MouseEvent event) {validateForm();}

    /**
     * onKeyPressed. This event allows the validateForm call when a user tabs through the form.
     * @param event
     */
    @FXML void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB) {
            validateForm();
        }
    }

    /**
     * updateApptIDCLick. This event calls the validateForm() method to assure data input.
     * @param event
     */
    @FXML void updateApptApptIDClick(ActionEvent event) {
        validateForm();
    }

    /**
     * updateApptCancelClick. This event sends the user back to the Main Menu.
     * @param event
     * @throws IOException
     */
    @FXML void updateApptCancelClick(ActionEvent event) throws IOException {
        alert(8);
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = new Scene(FXMLLoader.load(getClass().getResource("/DBClientApp/view/Menu.fxml")));
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * updateApptContactClick. This event calls the validateForm() method to assure data input.
     * @param event
     */
    @FXML void updateApptContactClick(ActionEvent event) {
        validateForm();
    }

    /**
     * updateApptCustIDClick. This event calls the validateForm() method to assure data input.
     * @param event
     */
    @FXML void updateApptCustIDClick(ActionEvent event) {
        validateForm();
    }

    /**
     * updateApptDescriptionClick. This event calls the validateForm() method to assure data input.
     * @param event
     */
    @FXML void updateApptDescriptionClick(ActionEvent event) {
        validateForm();
    }

    /**
     * updateApptEDateClick. The action verifies the logic behind setting an end date, and sets the times for the end time combo box.
     * If the end date is on the same day as the start day, the end times will reflect 5 minutes after the start time through to closing.
     * Otherwise, they will be available for all business hours.
     * @param event
     */
    @FXML void updateApptEDateClick(ActionEvent event) {
        // get this method's instance of the conversion.
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
                if (updateApptEDateCal.getValue().equals(LocalDate.now()) && updateApptSDateCal.getValue() != null && updateApptSTimeCombo.getValue() != null && updateApptSDateCal.getValue().equals(LocalDate.now())) {
                    updateApptETimeCombo.getItems().clear();
                    LocalTime endStart = updateApptSTimeCombo.getValue().plusMinutes(5);//todayHourStart.toLocalTime();
                    while (endStart.isBefore(myHourEnd.toLocalTime().plusSeconds(1))) {
                        updateApptETimeCombo.getItems().add(LocalTime.from(endStart));
                        endStart = endStart.plusMinutes(5);
                    }
                }
                else if (updateApptEDateCal.getValue().equals(LocalDate.now())) {
                    updateApptETimeCombo.getItems().clear();
                    LocalTime endStart = todayHourStart.toLocalTime().plusMinutes(5);
                    while (endStart.isBefore(myHourEnd.toLocalTime().plusSeconds(1))) {
                        updateApptETimeCombo.getItems().add(LocalTime.from(endStart));
                        endStart = endStart.plusMinutes(5);
                    }
                }
                else {
                    updateApptETimeCombo.getItems().clear();
                    LocalTime endStart = myHourStart.toLocalTime();
                    while (endStart.isBefore(myHourEnd.toLocalTime().plusSeconds(1))) {
                        updateApptETimeCombo.getItems().add(LocalTime.from(endStart));
                        endStart = endStart.plusMinutes(5);
                    }
                }
            }
            updateApptETimeCombo.setPromptText("Select please...");
        }
        validateForm();
    }

    /**
     * updateApptETimeClick. This event verifies a logical end time and sets it.
     * @param event
     */
    @FXML void updateApptETimeClick(ActionEvent event) {
        if (updateApptSDateCal.getValue() != null && updateApptEDateCal.getValue() != null && updateApptSDateCal.getValue().equals(updateApptEDateCal.getValue())) {
            if (updateApptSTimeCombo.getSelectionModel().getSelectedItem() != null) {
                LocalTime endStart = updateApptSTimeCombo.getSelectionModel().getSelectedItem().plusMinutes(5);
                while (endStart.isBefore(myHourEnd.toLocalTime().plusSeconds(1))) {
                    updateApptETimeCombo.getItems().add(LocalTime.from(endStart));
                    endStart = endStart.plusMinutes(5);
                }
            }
            else {
                LocalTime localOpen = myHourStart.toLocalTime().plusMinutes(5);
                while (localOpen.isBefore(myHourEnd.toLocalTime().plusSeconds(1))) {
                    updateApptETimeCombo.getItems().add(LocalTime.from(localOpen));
                    localOpen = localOpen.plusMinutes(5);
                }
            }
        }
        validateForm();
    }

    /**
     * updateApptLocationCLick. This event calls the validateForm() method to assure data input.
     * @param event
     */
    @FXML void updateApptLocationClick(ActionEvent event) {
        validateForm();
    }

    /**
     * updateApptSDateClick. This event handles the logic behind picking a start date, and alerts on unaccessible dates.
     * The event sets the times in the combobox according to the start date. If the start date is on the current day,
     * only start times from then on are available.
     * When adding an appointment, an appointment that spans days is not allowed.
     * However, I built the update controller to allow for end date change, due to possible testing.
     * @param event
     */
    @FXML void updateApptSDateClick(ActionEvent event) {
        // get this method's instance of the conversion.
        int minute = LocalTime.now().getMinute();
        minute = (minute >= 0 && minute <= 59) ? minute : 0;
        int getTimeToAdd = minute % 5;
        int hour = LocalTime.now().getHour();
        int startMin;

        startMin = minute + (getTimeToAdd > 0 ? 5 - getTimeToAdd : 0);
        startMin = (startMin >= 0 && startMin <= 59) ? startMin : 0;

        ZonedDateTime todayStartConversion = ZonedDateTime.of(LocalDate.now(), LocalTime.of(hour, startMin), ZoneId.of(String.valueOf(userTime)));
        ZonedDateTime todayHourStart = todayStartConversion.withZoneSameInstant(userTime);
        // check for null date and then for the start date chosen. Can't be historical and if it starts today, the start time will have to be after the current time.

        if (updateApptSDateCal.getValue() != null) {
            if (updateApptSDateCal.getValue().isBefore(LocalDate.now())) {
                updateApptSDateCal.setValue(null);
                updateApptSDateCal.setPromptText("Select please...");
                // updateApptSTimeCombo.setValue(null);
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
        }
        validateForm();
    }

    /**
     * updateApptSTimeClick This event adjusts for the current time if an appointment is chosen today, and alerts if closed.
     * The method allows all business hours on any business day in the future.
     * The method then sets the end time combobox with options that start 5 minutes after the start time.
     * The last available start time is always 5 minutes prior to closing, to allow for a closing time end of meeting.
     * @param event
     */
    @FXML void updateApptSTimeClick(ActionEvent event) {
        if (updateApptSDateCal.getValue() != null && updateApptEDateCal.getValue() != null && updateApptSDateCal.getValue().equals(updateApptEDateCal.getValue())) {
            // updateApptSTimeCombo.getItems().clear();
            if (updateApptSTimeCombo.getSelectionModel().getSelectedItem() != null) {
                updateApptETimeCombo.getItems().clear();
                LocalTime endStart = updateApptSTimeCombo.getSelectionModel().getSelectedItem().plusMinutes(5);
                while (endStart.isBefore(myHourEnd.toLocalTime().plusSeconds(1))) {
                    updateApptETimeCombo.getItems().add(LocalTime.from(endStart));
                    endStart = endStart.plusMinutes(5);
                }
            }
            else {
                updateApptETimeCombo.getItems().clear();
                LocalTime localOpen = myHourStart.toLocalTime().plusMinutes(5);
                while (localOpen.isBefore(myHourEnd.toLocalTime().plusSeconds(1))) {
                    updateApptETimeCombo.getItems().add(LocalTime.from(localOpen));
                    localOpen = localOpen.plusMinutes(5);
                }
            }
        }
        validateForm();
    }

    /**
     * updateApptSaveClick. This method uses logic to assure dates and times are properly input and won't overlap.
     * The method verifies all data is input and cannot be enabled until the validateForm() call has returned true.
     * The method then uses the AppointmentData class to modify the appointment data.
     * On error the method alerts and returns to the form.
     * @param event
     */
    @FXML void updateApptSaveClick(ActionEvent event) {

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String thisStart = updateApptSDateCal.getValue() + " " + updateApptSTimeCombo.getSelectionModel().getSelectedItem();
        String thisEnd = updateApptEDateCal.getValue() + " " + updateApptETimeCombo.getSelectionModel().getSelectedItem();
        LocalDateTime thisStartDateTime = LocalDateTime.parse(thisStart, dateFormat);
        LocalDateTime thisEndDateTime = LocalDateTime.parse(thisEnd, dateFormat);

        ObservableList<Appointment> apptList = AppointmentData.getAllApps();
        ObservableList<Appointment> customerAppts = FXCollections.observableArrayList();
        for (Appointment a : apptList) {
            if (a.getCustomerID() == updateApptCustIDCombo.getSelectionModel().getSelectedItem()) {
                customerAppts.add(a);
            }
            for (Appointment appt : customerAppts) {
                LocalDateTime custStarts = LocalDateTime.parse(appt.getStart(), dateFormat);
                LocalDateTime custEnds = LocalDateTime.parse(appt.getEnd(), dateFormat);
                if (thisStartDateTime.equals(custStarts) && (chosenAppointment.getAppointmentID() != appt.getAppointmentID())) {
                    alert(9);
                    return;
                }
                else if (thisStartDateTime.isAfter(custStarts) && thisStartDateTime.isBefore(custEnds) && (chosenAppointment.getAppointmentID() != appt.getAppointmentID())) {
                    alert(9);
                    return;
                }
                else if (thisStartDateTime.isBefore(custStarts) && thisEndDateTime.isAfter(custStarts) && (chosenAppointment.getAppointmentID() != appt.getAppointmentID())) {
                    alert(9);
                    return;
                }
                else if (thisStartDateTime.isAfter(custStarts) && thisEndDateTime.isBefore(custEnds) && (chosenAppointment.getAppointmentID() != appt.getAppointmentID())) {
                    alert(9);
                    return;
                }
            }
        }
        try {
            Integer appointmentID = Integer.valueOf(updateApptApptIDTxt.getText());
            String title = updateApptTitleTxt.getText();
            String description = updateApptDescriptionTxt.getText();
            String location = updateApptLocationTxt.getText();
            String type = updateApptTypeTxt.getText();
            // make a timestamp from the selections
            LocalDateTime apptStartDate = LocalDateTime.of(updateApptSDateCal.getValue(), updateApptSTimeCombo.getSelectionModel().getSelectedItem());
            LocalDateTime apptEndDate = LocalDateTime.of(updateApptEDateCal.getValue(), updateApptETimeCombo.getSelectionModel().getSelectedItem());
            Timestamp startTS = Timestamp.valueOf(apptStartDate);
            Timestamp endTS = Timestamp.valueOf(apptEndDate);
            int custID = updateApptCustIDCombo.getSelectionModel().getSelectedItem();
            int userID = updateApptUserIDCombo.getSelectionModel().getSelectedItem();
            int contactID = updateApptContactCombo.getSelectionModel().getSelectedItem().getContactID();
            Timestamp emptyTS = Timestamp.valueOf("1970-01-01 00:00:00");
            if (title.isEmpty() || description.isEmpty() || location.isEmpty() || type.isEmpty() || startTS.equals(emptyTS) ||
                    endTS.equals(emptyTS)) {
                alert(4);
            }
            else {
                AppointmentData.updateAppointment(title, description, location, type, startTS, endTS, custID, userID, contactID, appointmentID);
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
     * updateApptTitleClick. This event calls the validateForm() method to assure data input.
     * @param event
     */
    @FXML void updateApptTitleClick(ActionEvent event) {
        validateForm();
    }

    /**
     * updateApptTypeClick. This event calls the validateForm() method to assure data input.
     * @param event
     */
    @FXML void updateApptTypeClick(ActionEvent event) {
        validateForm();
    }

    /**
     * updateApptUserIDClick. This event calls the validateForm() method to assure data input.
     * @param event
     */
    @FXML void updateApptUserIDClick(ActionEvent event) {
        validateForm();
    }

    /**
     * alert. This method is used to communicate various errors and information to the user.
      * @param alertNum
     */
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
            case 8 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("D'oh!");
                alert.setHeaderText("No Updates Made");
                alert.setContentText("Appointment # " + updateApptApptIDTxt.getText() + " has not been updated in the database!");
                alert.showAndWait();
            }
            case 9 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("D'oh!");
                alert.setHeaderText("So this is what it's like when appointments collide.");
                alert.setContentText("Your chosen appointment time overlaps with another appointment for this customer. Please pick another time.");
                alert.showAndWait();
            }
        }
    }

    /**
     * validateForm. This method is used to make sure all fields have been filled out and the date logic is sound.
     * The save button cannot be clicked unless this method returns true.
     */
    private void validateForm() {
        boolean formValidated = true;
        if (updateApptTitleTxt.getText().isEmpty() ||
                updateApptDescriptionTxt.getText().isEmpty() ||
                updateApptLocationTxt.getText().isEmpty() ||
                updateApptTypeTxt.getText().isEmpty() ||
                updateApptSDateCal.getValue() == null ||
                updateApptEDateCal.getValue() == null ||
                updateApptSTimeCombo.getSelectionModel().isEmpty() ||
                updateApptETimeCombo.getSelectionModel().isEmpty() ||
                updateApptContactCombo.getSelectionModel().isEmpty() ||
                updateApptCustIDCombo.getSelectionModel().isEmpty() ||
                updateApptUserIDCombo.getSelectionModel().isEmpty()) {
                formValidated = false;
        }
        else if (updateApptSDateCal.getValue().equals(updateApptEDateCal.getValue())) {
            if (updateApptSDateCal.getValue().isAfter(updateApptEDateCal.getValue()) || updateApptSTimeCombo.getValue().isAfter(updateApptETimeCombo.getValue())) {
                formValidated = false;
                alert(3);
            }
        }
        updateApptSaveBtn.setDisable(!formValidated);
    }

    /**
     * initialize. This method handles the logic of bringing in the data from the Main Menu, and applies it to the fields on the form.
     * An instance of the current time was needed in order to set various logic checks, that rely on each other if another is chosen.
     * For example, the times could not be chosen independently within the logic I used to fill the combo boxes.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateApptSaveBtn.setDisable(true);
        System.out.println("Update Appointment Controller Launched");

        /////////////////////////////////
        // Data the controller will need
        /////////////////////////////////

        // Get system timezone
        ZoneId userTime = ZoneId.systemDefault();

        // Get the times converted for combobox insertion
        // Start
        ZonedDateTime est2systemStart = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8, 0), ZoneId.of("America/New_York"));
        ZonedDateTime myHourStart = est2systemStart.withZoneSameInstant(userTime);

        // Time block to handle cases where an appointment begins today.
        int minute = LocalTime.now().getMinute();
        minute = (minute >= 0 && minute <= 59) ? minute : 0;
        int getTimeToAdd = minute % 5;
        int hour = LocalTime.now().getHour();
        int startMin = minute + (getTimeToAdd > 0 ? 5 - getTimeToAdd : 0);
        startMin = (startMin >= 0 && startMin <= 59) ? startMin : 0;
        ZonedDateTime todayStartConversion = ZonedDateTime.of(LocalDate.now(), LocalTime.of(hour, startMin), ZoneId.of(String.valueOf(userTime)));
        ZonedDateTime todayHourStart = todayStartConversion.withZoneSameInstant(userTime);

        //End
        ZonedDateTime est2systemEnd = ZonedDateTime.of(LocalDate.now(), LocalTime.of(22, 0), ZoneId.of("America/New_York"));
        ZonedDateTime myHourEnd = est2systemEnd.withZoneSameInstant(userTime);

        // Appointment object from the Main Menu
        chosenAppointment = MenuController.chooseYourAppointment();

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

        // Start Time Combo Box
        if (startDate.equals(LocalDate.now())) {
            if (todayHourStart.isAfter(myHourEnd)) {
                LocalTime localOpen = myHourStart.toLocalTime();
                while (localOpen.isBefore(myHourEnd.toLocalTime().minusMinutes(5).plusSeconds(1))) {
                    updateApptSTimeCombo.getItems().add(LocalTime.from(localOpen));
                    localOpen = localOpen.plusMinutes(5);
                }
            }
            else if (todayHourStart.isBefore(myHourStart)) {
                LocalTime localOpen = myHourStart.toLocalTime();
                while (localOpen.isBefore(myHourEnd.toLocalTime().plusSeconds(1))) {
                    updateApptSTimeCombo.getItems().add(LocalTime.from(localOpen));
                    localOpen = localOpen.plusMinutes(5);
                }
            }
            else {
                updateApptSTimeCombo.getItems().clear();
                LocalTime localOpen = todayHourStart.toLocalTime();
                while (localOpen.isBefore(myHourEnd.toLocalTime().minusMinutes(5).plusSeconds(1))) {
                    updateApptSTimeCombo.getItems().add(LocalTime.from(localOpen));
                    localOpen = localOpen.plusMinutes(5);
                }
            }
        }
        else {
            LocalTime localOpen = myHourStart.toLocalTime();
            while (localOpen.isBefore(myHourEnd.toLocalTime().minusMinutes(5).plusSeconds(1))) {
                updateApptSTimeCombo.getItems().add(LocalTime.from(localOpen));
                localOpen = localOpen.plusMinutes(5);
            }
        }

        // End Time Combo Box
        if (endDate.equals(LocalDate.now())) {
            if (todayHourStart.isAfter(myHourEnd)) {
                LocalTime endStart = myHourStart.toLocalTime().plusMinutes(5);
                while (endStart.isBefore(myHourEnd.toLocalTime().plusSeconds(1))) {
                    updateApptETimeCombo.getItems().add(LocalTime.from(endStart));
                    endStart = endStart.plusMinutes(5);
                }
            }
            else if (todayHourStart.isBefore(myHourStart)) {
                LocalTime endStart = myHourStart.toLocalTime().plusMinutes(5);
                while (endStart.isBefore(myHourEnd.toLocalTime().plusSeconds(1))) {
                    updateApptETimeCombo.getItems().add(LocalTime.from(endStart));
                    endStart = endStart.plusMinutes(5);
                }
            }
            else {
                updateApptETimeCombo.getItems().clear();
                LocalTime endStart = todayHourStart.toLocalTime().plusMinutes(5);
                while (endStart.isBefore(myHourEnd.toLocalTime().plusSeconds(1))) {
                    updateApptETimeCombo.getItems().add(LocalTime.from(endStart));
                    endStart = endStart.plusMinutes(5);
                }
            }
        }
        else {
            LocalTime endStart = myHourStart.toLocalTime().plusMinutes(5);
            while (endStart.isBefore(myHourEnd.toLocalTime().plusSeconds(1))) {
                updateApptETimeCombo.getItems().add(LocalTime.from(endStart));
                endStart = endStart.plusMinutes(5);
            }
        }

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
        updateApptETimeCombo.setValue(endTime);

        // Create lists: Contacts, Customers, Users - assign original appt values.
        updateApptContactCombo.setItems(contacts);
        updateApptContactCombo.setValue(thisContact);
        updateApptCustIDCombo.setItems(customerIDs);
        updateApptCustIDCombo.setValue(chosenAppointment.getCustomerID());
        updateApptUserIDCombo.setItems(userIDs);
        updateApptUserIDCombo.setValue(chosenAppointment.getUserID());

        // Check to see if the appointment has details that have already passed
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
    }
}
