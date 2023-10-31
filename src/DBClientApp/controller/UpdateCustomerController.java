package DBClientApp.controller;

import DBClientApp.DAO.CountryData;
import DBClientApp.DAO.CustomerData;
import DBClientApp.DAO.DivisionData;
import DBClientApp.model.Country;
import DBClientApp.model.Customer;
import DBClientApp.model.FirstLevelDivision;
import DBClientApp.model.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


/**
 * UpdateCustomerController class. This class handles the logic behind updating the customer object/table.
 */
public class UpdateCustomerController implements Initializable {

    // UpdateCustomerController Attributes
    Stage stage;
    Scene scene;
    static Customer chosenCustomer; // This object is the customer from the main customer table.
    @FXML private Pane updateCustPane;
    @FXML private Button updateCustSaveBtn;
    @FXML private Button updateCustCancelBtn;
    @FXML private ComboBox<Country> updateCustCountryCombo;
    @FXML private ComboBox<FirstLevelDivision> updateCustDivisionCombo;
    @FXML private Label divisionLabel;
    @FXML private TextField updateCustAddressTxt;
    @FXML private TextField updateCustIDTxt;
    @FXML private TextField updateCustNameTxt;
    @FXML private TextField updateCustPhoneTxt;
    @FXML private TextField updateCustPostalCodeTxt;

    /**
     * onMouseClicked. This event, in conjunction with the updateCustPane to request the mouse focus when the user clicks outside a textfield.
     * @param event
     */
    @FXML void onMouseClicked(MouseEvent event) {
        updateCustPane.requestFocus();
    }

    /**
     * updateCustCancelClick. This method sends the user back to the main menu page, due to cancelling the update/modificaion.
     * @param event
     * @throws IOException
     */
    @FXML void updateCustCancelClick(ActionEvent event) throws IOException {
        alert(2);
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = new Scene(FXMLLoader.load(getClass().getResource("/DBClientApp/view/Menu.fxml")));
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * updateCustCountryClick. This event updates the updateCustDivisionCombo box with the divisions from the customer's
     * chosen country.
     * @param event
     */
    @FXML void updateCustCountryClick(ActionEvent event) {
        ObservableList<FirstLevelDivision> theseDivisions;
        theseDivisions = DivisionData.getTheseDivisions(updateCustCountryCombo.getSelectionModel().getSelectedItem().getCountryID());
        updateCustDivisionCombo.setItems(theseDivisions);
    }

    //////////////////////////////////////////////////////////////////
    // Created these event handlers but never used them in this class.
    //////////////////////////////////////////////////////////////////
    @FXML void updateCustAddressClick(ActionEvent event) {}
    @FXML void updateCustDivisionClick(ActionEvent event) {
    }
    @FXML void updateCustIDClick(ActionEvent event) {

    }
    @FXML void updateCustNameClick(ActionEvent event) {

    }
    @FXML void updateCustPhoneClick(ActionEvent event) {

    }
    @FXML void updateCustPostalCodeClick(ActionEvent event) {
    }

    /**
     * updateCustSaveClick. This method uses the boolean updateable to verify the customer data is properly input,
     * and saves the data to the database.
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML void updateCustSaveClick(ActionEvent event) throws SQLException, IOException {
        FirstLevelDivision division = updateCustDivisionCombo.getSelectionModel().getSelectedItem();
        int customerID = chosenCustomer.getCustomerID();
        int divisionID = DivisionData.getDivisionID(String.valueOf(division));
        String customerName = updateCustNameTxt.getText();
        String address = updateCustAddressTxt.getText();
        String postalCode = updateCustPostalCodeTxt.getText();
        String phone = updateCustPhoneTxt.getText();
        String user = LoginController.getUserName();

        // Logic checks
        boolean updateable = true;
        if (customerName.isEmpty() || address.isEmpty() || postalCode.isEmpty() || phone.isEmpty() ||
                divisionID < 1 || customerID < 1) {
            updateable = false;
            alert(3);
        }
        if (updateable) {
            int updatedInt = CustomerData.updateCustomer(customerName, address, postalCode, phone, user, divisionID, customerID);

            if (updatedInt >= 1) {
                alert(1);
                scene = new Scene(FXMLLoader.load(getClass().getResource("/DBClientApp/view/Menu.fxml")));
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.setTitle("Main Menu");
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
            }
            else {
                alert(2);
                scene = new Scene(FXMLLoader.load(getClass().getResource("/DBClientApp/view/Menu.fxml")));
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.setTitle("Main Menu");
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
            }
        }
    }

    /**
     * alert. This set of alerts handles the various communications to the user.
      * @param alertNum
     * @throws IOException
     */
    private void alert(int alertNum) throws IOException {
        switch (alertNum) {
            case 1 -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Success!");
                alert.setHeaderText("Customer Record Updated");
                alert.setContentText("Great work, you've updated " + updateCustNameTxt.getText() + "'s information.");
                alert.showAndWait();
            }
            case 2 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("D'oh!");
                alert.setHeaderText("No Updates Made");
                alert.setContentText(updateCustNameTxt.getText() + " Has not been updated in the database!");
                alert.showAndWait();
            }
            case 3 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("D'oh!");
                alert.setHeaderText("Invalid Customer Data");
                alert.setContentText("Please fill out all data rows and re-submit");
                alert.showAndWait();
            }
        }
    }

    /**
     *  Lambda Expression #1 gets all countries and assigns the Main Menu's selected customer's country to the combobox for updating.
     *  Lambda Expression #2 takes divisions that have been separated per the selected customer's country, then assigns the combobox appropriately for updating.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Update Customer Controller Launched");
        chosenCustomer = MenuController.chooseYourCustomer();
        ObservableList<Country> countries = CountryData.getCountry();

        ObservableList<FirstLevelDivision> divisions = DivisionData.getDivisions();
        String divisionName = DivisionData.getDivisionName(chosenCustomer.getDivisionID());

        updateCustIDTxt.setText(String.valueOf(chosenCustomer.getCustomerID()));
        updateCustNameTxt.setText(chosenCustomer.getCustomerName());
        updateCustAddressTxt.setText(chosenCustomer.getAddy());
        updateCustPhoneTxt.setText(chosenCustomer.getPhone());
        updateCustCountryCombo.setItems(countries);
        updateCustDivisionCombo.setItems(divisions);
        /**
         * Lambda expression #1 takes the countries and assigns the country to the combo box based on the selected customer from the menu screen.
         */
        countries.forEach(c->{
            if (c.getCountryName().equals(chosenCustomer.getCountryName())) {
                updateCustCountryCombo.setValue(c);
            }
        });
        // set the division label and division combo box selections
        int countryID = updateCustCountryCombo.getSelectionModel().getSelectedItem().getCountryID();
        ObservableList<FirstLevelDivision> theseDivisions;

        switch (countryID) {
            case 1 -> {
                divisionLabel.setText("State | Territory");
                updateCustDivisionCombo.getSelectionModel().clearSelection();
                theseDivisions = DivisionData.getTheseDivisions(countryID);
                updateCustDivisionCombo.setDisable(false);
                updateCustDivisionCombo.setItems(theseDivisions);

                /**Lambda expression #2 takes "theseDivisions", which are the first level divisions of a selected country.
                 * The divisions are compared against the division name of the chosen customer string above (line 197).
                 * The combo box item is then set to the selected customer's division.
                 */
                theseDivisions.forEach(f->{
                    if (f.getDivision().equals(divisionName)) {
                        updateCustDivisionCombo.setValue(f);
                    }
                });
            }
            case 2 -> {
                divisionLabel.setText("Province | Country");
                updateCustDivisionCombo.getSelectionModel().clearSelection();
                theseDivisions = DivisionData.getTheseDivisions(countryID);
                updateCustDivisionCombo.setDisable(false);
                updateCustDivisionCombo.setItems(theseDivisions);
                for (FirstLevelDivision f : theseDivisions) {
                    if (f.getDivision().equals(divisionName)) {
                        updateCustDivisionCombo.setValue(f);
                    }
                }
            }
            case 3 -> {
                divisionLabel.setText("Province | Territory");
                updateCustDivisionCombo.getSelectionModel().clearSelection();
                theseDivisions = DivisionData.getTheseDivisions(countryID);
                updateCustDivisionCombo.setDisable(false);
                updateCustDivisionCombo.setItems(theseDivisions);
                for (FirstLevelDivision f : theseDivisions) {
                    if (f.getDivision().equals(divisionName)) {
                        updateCustDivisionCombo.setValue(f);
                    }
                }
            }
            case 0 -> {
                divisionLabel.setText("Province | Territory");
                updateCustDivisionCombo.getSelectionModel().clearSelection();
                updateCustDivisionCombo.setDisable(true);
            }
            default -> {
                divisionLabel.setText("Prov. | State | Terr.");
                updateCustDivisionCombo.getSelectionModel().clearSelection();
                updateCustDivisionCombo.setDisable(true);
            }
        }
        updateCustPostalCodeTxt.setText(chosenCustomer.getPostalCode());
    }
}
