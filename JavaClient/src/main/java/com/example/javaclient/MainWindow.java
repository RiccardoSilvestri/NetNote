package com.example.javaclient;

import com.example.javaclient.PackageTestingRiccardo.SendCredentials;
import com.example.javaclient.notes.Note;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

public class MainWindow extends Application {
    private TextField usernameField;
    private PasswordField passwordField;
    private static final String SERVER_NAME = "localhost";
    private static final int PORT = 4444;
    @Override
    public void start(Stage stage) throws IOException {
        // Load the user interface from an FXML file
        VBox root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginRegister.fxml")));
        Scene scene = new Scene(root, 600, 400);
        stage.setResizable(false);
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icon.png")));
        // Set the icon for the stage
        stage.getIcons().add(icon);

        stage.setTitle("NetNote Sign in");
        stage.setScene(scene);
        stage.show();

        // Obtain references to the text fields and buttons
        usernameField = (TextField) scene.lookup("#usernameField");
        passwordField = (PasswordField) scene.lookup("#passwordField");
        Button registerButton = (Button) scene.lookup("#SignUpButton");
        Button loginButton = (Button) scene.lookup("#SignInButton");

        // Register an event handler for the register button
        registerButton.setOnAction(event -> {
            try {
                register();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        loginButton.setOnAction(event -> {
            try {
                login();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }


    private void register() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        Socket client = Connection.InitConnection(SERVER_NAME, PORT);
        int returndelserver = SendCredentials.sendCredentials(client, "1", username, password);

        Stage stage = (Stage) usernameField.getScene().getWindow();

        if (returndelserver == 0) {
            System.out.println("Register Failed");

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Register Failed");
            alert.setHeaderText("registration failed");
            alert.setContentText("Already registered user.");
            alert.showAndWait();
        } else {
            stage.close();
            Note note = new Note();
            note.notes(client, username);
        }
    }

    private void login() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        Socket client = Connection.InitConnection(SERVER_NAME, PORT);
        int returndelserver = SendCredentials.sendCredentials(client, "2", username, password);

        if (returndelserver == 0) {
            System.out.println("Login Failed");

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText("Failed Login");
            alert.setContentText("Name or Password is wrong!.");
            alert.showAndWait();
        } else {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.close();
            Note note = new Note();
            note.notes(client, username);
        }
    }


    public static void main(String[] args) {
        launch();
    }

    public void saveFile(ActionEvent actionEvent) {

    }
}
