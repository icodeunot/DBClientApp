package DBClientApp.controller;

import DBClientApp.DAO.CountryData;
import DBClientApp.DAO.CustomerData;
import DBClientApp.DAO.DivisionData;
import DBClientApp.model.Country;
import DBClientApp.model.FirstLevelDivision;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * AddCustomerController class. This class handles the logic for adding a customer to the database.
 */
public class AddCustomerController implements Initializable {

    // AddCustomerContrller Attributes
    Stage stage;
    Scene scene;

    @FXML private Pane addCustPane;
    @FXML private Button addCustCancelBtn;
    @FXML private Button addCustSaveBtn;
    @FXML private ComboBox<Country> addCustCountryCombo;
    @FXML private ComboBox<FirstLevelDivision> addCustDivisionCombo;
    @FXML private Label divisionLabel;
    @FXML private TextField addCustIDTxt;
    @FXML private TextField addCustAddressTxt;
    @FXML private TextField addCustNameTxt;
    @FXML private TextField addCustPhoneTxt;
    @FXML private TextField addCustPostalCodeTxt;

    /**
     * onMouseClicked. This event requests focus of the mouse when the user clicks out of the text fields.
      * @param event
     */
    @FXML void onMouseClicked(MouseEvent event) {
        addCustPane.requestFocus();
    }

    /**
     * addCustCancelClick. This event sends the user back to the main menu.
     * @param event
     * @throws IOException
     */
    @FXML void addCustCancelClick(ActionEvent event) throws IOException {
        scene = new Scene(FXMLLoader.load(getClass().getResource("/DBClientApp/view/Menu.fxml")));
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * addCustCountryClick. This method uses the chosen country to fill the division combobox per the chosen country.
     * @param event
     */
    @FXML void addCustCountryClick(ActionEvent event) {

        int countryID = addCustCountryCombo.getSelectionModel().getSelectedItem().getCountryID();
        ObservableList<FirstLevelDivision> theseDivisions;

        switch (countryID) {
            case 1 -> {
                divisionLabel.setText("State | Territory");
                addCustDivisionCombo.getSelectionModel().clearSelection();
                theseDivisions = DivisionData.getTheseDivisions(countryID);
                addCustDivisionCombo.setDisable(false);
                addCustDivisionCombo.setItems(theseDivisions);
            }
            case 2 -> {
                divisionLabel.setText("Province | Country");
                addCustDivisionCombo.getSelectionModel().clearSelection();
                theseDivisions = DivisionData.getTheseDivisions(countryID);
                addCustDivisionCombo.setDisable(false);
                addCustDivisionCombo.setItems(theseDivisions);
            }
            case 3 -> {
                divisionLabel.setText("Province | Territory");
                addCustDivisionCombo.getSelectionModel().clearSelection();
                theseDivisions = DivisionData.getTheseDivisions(countryID);
                addCustDivisionCombo.setDisable(false);
                addCustDivisionCombo.setItems(theseDivisions);
            }
            case 0 -> {
                divisionLabel.setText("Province | Territory");
                addCustDivisionCombo.getSelectionModel().clearSelection();
                addCustDivisionCombo.setDisable(true);
            }
            default -> {
                divisionLabel.setText("Prov. | State | Terr.");
                addCustDivisionCombo.getSelectionModel().clearSelection();
                addCustDivisionCombo.setDisable(true);
            }
        }
    }

    /**
     * addCustDivisionClick. This method fills the divisionID attribute for addition to the database.
     * @param event
     */
    @FXML void addCustDivisionClick(ActionEvent event) {
        int divisionID = DivisionData.getDivisionID(String.valueOf(addCustDivisionCombo.getSelectionModel().getSelectedItem()));
    }

    ///////////////////////////////////////////////////////
    // These events aren't really used.
    ///////////////////////////////////////////////////////

    @FXML void addCustAddressClick(ActionEvent event) {}
    @FXML void addCustIDClick(ActionEvent event) {

    }
    @FXML void addCustNameClick(ActionEvent event) {

    }
    @FXML void addCustPhoneClick(ActionEvent event) {

    }
    @FXML void addCustPostalCodeClick(ActionEvent event) {

    }

    /**
     * addCustSaveClick. Unlike the Add Customer save method. This method does make sure all fields have input.
     * @param event
     * @throws IOException
     */
    @FXML void addCustSaveClick(ActionEvent event) throws IOException {

        try {
            String customerName = addCustNameTxt.getText();
            String address = addCustAddressTxt.getText();
            String postalCode = addCustPostalCodeTxt.getText();
            String phone = addCustPhoneTxt.getText();
            String createdBy = LoginController.getUserName();
            String lastUpdatedBy = LoginController.getUserName();
            int divisionID = DivisionData.getDivisionID(String.valueOf(addCustDivisionCombo.getSelectionModel().getSelectedItem()));

            if (customerName.isEmpty() || address.isEmpty() || postalCode.isEmpty() || phone.isEmpty() || divisionID < 0) {
                alert(1);
            }
            else {
                CustomerData.insertCustomer(customerName, address, postalCode, phone, createdBy, lastUpdatedBy, divisionID);
                alert(2);
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
     * alert. This method communicates various errors and information to the user.
      * @param alertNum
     * @throws IOException
     */
    private void alert(int alertNum) throws IOException {
        switch (alertNum) {
            case 1 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("D'oh!");
                alert.setHeaderText("Invalid Customer Data");
                alert.setContentText("Please fill out all data rows and re-submit");
                alert.showAndWait();
            }
            case 2 -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setHeaderText("Customer Saved");
                alert.setContentText(addCustNameTxt.getText() + " Has been saved to the database!");
                alert.showAndWait();
            }
        }
    }

    /**
     * initialize. This method initializes the customer form with countries from the combo box.
     * I added a blank row in case the user did not want to pick a country yet.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Add Customer Controller Launched");
        ObservableList<Country> countries = CountryData.getCountry();
        countries.add(new Country("", -1));
        addCustCountryCombo.setItems(countries);
        addCustDivisionCombo.setDisable(true);
    }
}