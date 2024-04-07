package com.example.javaclient;

import com.example.javaclient.PackageTestingRiccardo.SendCredentials;
import com.example.javaclient.notes.Note;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class UserManagement {

    private static boolean checkCredentials(String username, String password){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Credentials error!");
        alert.setHeaderText("Credentials error!");
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
        alert.showAndWait();
        return false;
    }

    public static void register(TextField usernameField, PasswordField passwordField, Socket client) throws IOException {
        String username = usernameField.getText().toLowerCase();
        String password = passwordField.getText();
        if (!checkCredentials(username, password))
            return;
        int serverReturn = SendCredentials.sendCredentials(client, "1", username, password);
        Stage stage = (Stage) usernameField.getScene().getWindow();
        if (serverReturn == 0) {
            System.out.println("Register Failed");

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Register Failed");
            alert.setHeaderText("registration failed");
            alert.setContentText("User already registered.");
            alert.showAndWait();
        } else {
            stage.close();
            Note note = new Note();
            note.notes(client, username);
        }
    }

    public static void login(TextField usernameField, PasswordField passwordField, Socket client) throws IOException {
        String username = usernameField.getText().toLowerCase();
        String password = passwordField.getText();
        int serverReturn = SendCredentials.sendCredentials(client, "2", username, password);

        if (serverReturn == 0) {
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
}
