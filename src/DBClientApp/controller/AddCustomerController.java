package DBClientApp.controller;

import DBClientApp.DAO.CountryData;
import DBClientApp.DAO.CustomerData;
import DBClientApp.DAO.DivisionData;
import DBClientApp.model.Country;
import DBClientApp.model.FirstLevelDivision;
import DBClientApp.model.User;
import javafx.collections.FXCollections;
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

public class AddCustomerController implements Initializable {

    Stage stage;
    Scene scene;

    @FXML
    private Pane addCustPane;

    @FXML
    private Label divisionLabel;

    @FXML
    private TextField addCustAddressTxt;

    @FXML
    private Button addCustCancelBtn;

    @FXML
    private ComboBox<Country> addCustCountryCombo;

    @FXML
    private ComboBox<FirstLevelDivision> addCustDivisionCombo;

    @FXML
    private TextField addCustIDTxt;

    @FXML
    private TextField addCustNameTxt;

    @FXML
    private TextField addCustPhoneTxt;

    @FXML
    private TextField addCustPostalCodeTxt;

    @FXML
    private Button addCustSaveBtn;

    @FXML
    void onMouseClicked(MouseEvent event) {
        addCustPane.requestFocus();
    }

    @FXML
    void addCustAddressClick(ActionEvent event) {

    }

    @FXML
    void addCustCancelClick(ActionEvent event) throws IOException {
        scene = new Scene(FXMLLoader.load(getClass().getResource("/DBClientApp/view/Menu.fxml")));
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void addCustCountryClick(ActionEvent event) {
        // String country = addCustCountryCombo.getValue().toString();
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

    @FXML
    void addCustDivisionClick(ActionEvent event) {
        int divisionID = DivisionData.getDivisionID(String.valueOf(addCustDivisionCombo.getSelectionModel().getSelectedItem()));
    }

    @FXML
    void addCustIDClick(ActionEvent event) {

    }

    @FXML
    void addCustNameClick(ActionEvent event) {

    }

    @FXML
    void addCustPhoneClick(ActionEvent event) {

    }

    @FXML
    void addCustPostalCodeClick(ActionEvent event) {

    }

    @FXML
    void addCustSaveClick(ActionEvent event) throws IOException {

        try {
            String customerName = addCustNameTxt.getText();
            String address = addCustAddressTxt.getText();
            String postalCode = addCustPostalCodeTxt.getText();
            String phone = addCustPhoneTxt.getText();
            String createdBy = "mYtEST"; // User.getUserName();
            String lastUpdatedBy = "mYtEST"; // User.getLastUpdatedBy();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Add Customer Controller Launched");
        ObservableList<Country> countries = CountryData.getCountry();
        countries.add(new Country("", -1));
        addCustCountryCombo.setItems(countries);
        addCustDivisionCombo.setDisable(true);
    }
}