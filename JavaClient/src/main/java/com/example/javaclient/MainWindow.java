package com.example.javaclient;

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
        // Carica l'interfaccia utente da un file FXML
        VBox root = FXMLLoader.load(getClass().getResource("LoginRegister.fxml"));
        Scene scene = new Scene(root, 670, 420);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        // Ottiene riferimenti ai campi di testo e al pulsante di registrazione
        usernameField = (TextField) scene.lookup("#usernameField");
        passwordField = (PasswordField) scene.lookup("#passwordField");
        Button registerButton = (Button) scene.lookup("#RegisterButton");

        // Registra un gestore per il clic sul pulsante di registrazione
        registerButton.setOnAction(event -> {
            try {
                register();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    // Metodo per registrare un nuovo utente
    private void register() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        Register.register(username, password);

        // Chiude la finestra corrente
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.close();

        // Apre una nuova finestra
        Stage newStage = new Stage();
        VBox newRoot = FXMLLoader.load(getClass().getResource("NewWindow.fxml"));
        Scene newScene = new Scene(newRoot, 800, 600);
        newStage.setScene(newScene);
        newStage.setTitle("New Window");
        newStage.show();
    }


    // Punto di ingresso dell'applicazione
    public static void main(String[] args) {
        launch();
    }
}
