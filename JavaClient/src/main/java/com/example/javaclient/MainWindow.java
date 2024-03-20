package com.example.javaclient;
import com.example.javaclient.PackageTestingRiccardo.Login;
import com.example.javaclient.PackageTestingRiccardo.Register;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

public class MainWindow extends Application {
    private TextField usernameField;
    private PasswordField passwordField;

    @Override
    public void start(Stage stage) throws IOException {
        // Load the user interface from an FXML file
        VBox root = FXMLLoader.load(getClass().getResource("LoginRegister.fxml"));
        Scene scene = new Scene(root, 670, 420);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        // Obtain references to the text fields and buttons
        usernameField = (TextField) scene.lookup("#usernameField");
        passwordField = (PasswordField) scene.lookup("#passwordField");
        Button registerButton = (Button) scene.lookup("#RegisterButton");
        Button loginButton = (Button) scene.lookup("#LoginButton");

        // Register an event handler for the register button
        registerButton.setOnAction(event -> {
            try {
                register();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Register an event handler for the login button
        loginButton.setOnAction(event -> {
            try {
                login(); // Renamed to follow Java naming conventions
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    // Method to register a new user
    private void register() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        int returndelserver=Register.register(username, password);

        // Close the current window
        Stage stage = (Stage) usernameField.getScene().getWindow();

        if(returndelserver==0){
            System.out.println("Registrazione Fallita");

        }else{
            stage.close();
        }
        stage.close();

        // Open a new window
        Stage newStage = new Stage();
        VBox newRoot = FXMLLoader.load(getClass().getResource("NewWindow.fxml"));
        Scene newScene = new Scene(newRoot, 800, 600);
        newStage.setScene(newScene);
        newStage.setTitle("New Window");
        newStage.show();
    }

    // Method to log in an existing user
    private void login() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        int returndelserver = Login.login(username, password);
        // Close the current window
        Stage stage = (Stage) usernameField.getScene().getWindow();

        if(returndelserver==0){
            System.out.println("Login Fallito");

        }else{
            stage.close();
        }


        // Open a new window
        Stage newStage = new Stage();
        VBox newRoot = FXMLLoader.load(getClass().getResource("NewWindow.fxml"));
        Scene newScene = new Scene(newRoot, 800, 600);
        newStage.setScene(newScene);
        newStage.setTitle("New Window");
        newStage.show();

    }

    // Entry point of the application
    public static void main(String[] args) {
        launch();
    }
}
