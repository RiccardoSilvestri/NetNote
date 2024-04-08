package com.example.javaclient;

import com.example.javaclient.PackageTestingRiccardo.SendCredentials;
import com.example.javaclient.notes.Note;
import com.example.javaclient.utils.GetSha;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class UserManagement {

    // Method to check user credentials before registration
    private static boolean checkCredentials(String username, String password){

        // Creating an alert for credential errors
        Alert alert = new Alert(Alert.AlertType.ERROR);
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
    public static void register(TextField usernameField, PasswordField passwordField, Socket client) throws IOException {
        String username = usernameField.getText().toLowerCase();
        String password = passwordField.getText();

        // check of credentials
        if (!checkCredentials(username, password))
            return;

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
        } else {

            // Closing the register window and opening the notes window
            stage.close();
            Note note = new Note();
            note.notes(client, username);
        }
    }

    // Method to login a user
    public static void login(TextField usernameField, PasswordField passwordField, Socket client) throws IOException {
        String username = usernameField.getText().toLowerCase();
        String password = passwordField.getText();

        // Sending credentials to the server for login
        int serverReturn = SendCredentials.sendCredentials(client, "2", username, GetSha.getSHA256(password));

        // management server response
        if (serverReturn == 0) {
            System.out.println("Login Failed");

            //display an alert in case of login failure
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText("Failed Login");
            alert.setContentText("Name or Password is wrong!.");
            alert.showAndWait();
        } else {
            // Obtaining the current window to manage closing after login
            Stage stage = (Stage) usernameField.getScene().getWindow();

            // Closing the login window and opening the notes window
            stage.close();
            Note note = new Note();
            note.notes(client, username);
        }
    }
}
