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
        int returndelserver = SendCredentials.register(username, password);

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
            note.notes();
        }
    }

    private void login() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        int returndelserver = SendCredentials.login(username, password);

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
            note.notes();
        }
    }


    public static void main(String[] args) {
        launch();
    }

    public void saveFile(ActionEvent actionEvent) {

    }
}
