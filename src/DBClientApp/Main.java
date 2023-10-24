package DBClientApp;

import DBClientApp.helper.JDBC;
import DBClientApp.DAO.PracticeQuery;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void init() {
        System.out.println("Started.");
        System.out.println("Please update the Parent root in Main.java");
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Parent root = FXMLLoader.load(getClass().getResource("view/Login.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/DBClientApp/view/Menu.fxml")); // for testing only
        primaryStage.setTitle("Login Menu");
        primaryStage.setScene(new Scene(root));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    @Override
    public void stop() {
        System.out.println("Fin.");
    }

    public static void main(String[] args) throws SQLException {
        JDBC.openConnection();
        //  Locale.setDefault(new Locale("fr"));  // test for French
        launch(args);
        JDBC.closeConnection();
    }
}