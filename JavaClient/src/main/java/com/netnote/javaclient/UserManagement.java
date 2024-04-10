package com.netnote.javaclient;

import com.netnote.javaclient.PackageTestingRiccardo.SendCredentials;
import com.netnote.javaclient.notes.Note;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class UserManagement {

    private static boolean checkConnection(boolean serverStatus){
        if (!serverStatus){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connection Error!");
            alert.setHeaderText("Not connected to server");
            alert.setContentText("Please wait to connect to server");
            alert.showAndWait();
            return false;
        }
        return true;
    }
    // Method to check user credentials before registration
    private static boolean checkCredentials(String username, String password, boolean serverStatus){

        // Creating an alert for credential errors
        Alert alert = new Alert(Alert.AlertType.ERROR);

        // if client is not connected, print the warning
        if (!checkConnection(serverStatus))
            return false;

        if (!serverStatus){
            alert.setTitle("Connection Error!");
            alert.setHeaderText("Not connected to server");
            alert.setContentText("Please wait to connect to server");
            alert.showAndWait();
            return false;
        }
        alert.setTitle("Credentials error!");
        alert.setHeaderText("Credentials error!");

        // Credential requirements
        if (username.length() < 3){
            alert.setContentText("Username must be at least 3 characters.");
        } else if (username.contains(" ")) {
            alert.setContentText("Username must not have spaces.");
        } else if (password.length() < 5) {
            alert.setContentText("Password must be at least 5 characters.");
        } else if (password.contains(" ")){
            alert.setContentText("Password must not have spaces.");
        } else
            return true;

        // Alert display in case of error and false return
        alert.showAndWait();
        return false;
    }

    // Method for register a new user
    public static boolean register(TextField usernameField, PasswordField passwordField, Socket client, boolean serverStatus) throws IOException {
        String username = usernameField.getText().toLowerCase();
        String password = passwordField.getText();

        // check of credentials
        if (!checkCredentials(username, password, serverStatus))
            return false;

        // Sending credentials to the server for registration
        int serverReturn = SendCredentials.sendCredentials(client, "1", username, password);

        // Obtaining the current window to manage the closing after registration
        Stage stage = (Stage) usernameField.getScene().getWindow();

        // management server response
        if (serverReturn == 0) {
            System.out.println("Register Failed");

            //display of an alert in case of registration failure
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Register Failed");
            alert.setHeaderText("registration failed");
            alert.setContentText("User already registered.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    // Method to login a user
    public static boolean login(TextField usernameField, PasswordField passwordField, Socket client, boolean serverStatus) throws IOException {
        String username = usernameField.getText().toLowerCase();
        String password = passwordField.getText();

        if (!checkConnection(serverStatus))
            return false;
        // Sending credentials to the server for login
        int serverReturn = SendCredentials.sendCredentials(client, "2", username, password);

        // management server response
        if (serverReturn == 0) {
            System.out.println("Login Failed");

            //display an alert in case of login failure
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText("Failed Login");
            alert.setContentText("Name or Password is wrong!.");
            alert.showAndWait();
            return false;
        }
        return true;
    }
}
