package DBClientApp;

import DBClientApp.helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

import java.sql.SQLException;
import java.util.Locale;

public class Main extends Application {

    @Override
    public void init() {
        System.out.println("Started.");
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view/Login.fxml"));
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