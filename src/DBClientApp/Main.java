package DBClientApp;

import DBClientApp.helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;


/**
 * Main Class. This class starts the program, connects to the database, and ends the connection.
 */
public class Main extends Application {

    /**
     * Init. Initialized the program and says so in the cl.
     */
    @Override
    public void init() {
        System.out.println("Started.");
    }

    /**
     * start method. This method sends the user to the login form.
     * @param primaryStage
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view/Login.fxml")); // Skip for testing
        // Parent root = FXMLLoader.load(getClass().getResource("/DBClientApp/view/Menu.fxml")); // Set for testing

        primaryStage.setTitle("Login Menu");
        primaryStage.setScene(new Scene(root));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    /**
     * stop method. This method stops the program.
     */
    @Override
    public void stop() {
        System.out.println("Fin.");
    }

    /**
     * main method. This method launches the arrguments, opens and closes the database connection.
     * Also tests for French language.
     * @param args
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException {
        JDBC.openConnection();
        //  Locale.setDefault(new Locale("fr"));  // test for French
        launch(args);
        JDBC.closeConnection();
    }
}