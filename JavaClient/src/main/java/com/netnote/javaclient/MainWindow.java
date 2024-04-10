package com.netnote.javaclient;
import com.netnote.javaclient.notes.Note;
import com.netnote.javaclient.threads.EstablishConnectionThread;
import com.netnote.javaclient.threads.ServerStatusThread;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class MainWindow extends Application {
    private TextField usernameField;
    private PasswordField passwordField;
    private static final String SERVER_NAME = "localhost";
    private static final int PORT = 4444;

    // Start() method, javafx application entry point
    @Override
    public void start(Stage stage) throws IOException {

        // Load the user interface from an FXML file
        VBox root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginRegister.fxml")));
        Scene scene = new Scene(root, 600, 400);
        stage.setResizable(false);

        // Set the icon for the stage
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icon.png")));
        stage.getIcons().add(icon);

        // Setting the window title
        stage.setTitle("NetNote Sign in");
        stage.setScene(scene);
        stage.show();

        // Label to show server status
        Label ServerStatus = new Label(" ");
        ServerStatus.setAlignment(Pos.TOP_RIGHT);
        VBox.setMargin(ServerStatus, new Insets(10));
        root.getChildren().add(ServerStatus);

        // main connection to the server
        AtomicReference<Socket> client = new AtomicReference<>();

        // Thread to keep the connection to the server active
        EstablishConnectionThread.run(client, SERVER_NAME, PORT);

        // Spawns a new thread to test the connection and report the connection status
        ServerStatusThread.run(SERVER_NAME, PORT, ServerStatus);

        // Initialization of input fields and buttons for registration and login
        usernameField = (TextField) scene.lookup("#usernameField");
        passwordField = (PasswordField) scene.lookup("#passwordField");
        Button registerButton = (Button) scene.lookup("#SignUpButton");
        Button loginButton = (Button) scene.lookup("#SignInButton");

        // Action to perform when the register button is pressed
        registerButton.setOnAction(event -> {
            try {
                UserManagement.register(usernameField, passwordField, client.get(), Connection.isServerOnline(SERVER_NAME, PORT));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Action to perform when the login button is pressed
        loginButton.setOnAction(event -> {
            try {
                if (UserManagement.login(usernameField, passwordField, client.get(), Connection.isServerOnline(SERVER_NAME, PORT))){
                    // Closing the login window and opening the notes window
                    stage.close();
                    Note note = new Note();
                    note.notesWindow(client.get(), usernameField.getText().toLowerCase());
                    EstablishConnectionThread.terminate();
                };
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch();
    }
}