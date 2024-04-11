package com.netnote.javaclient;
import com.netnote.javaclient.threads.EstablishConnectionThread;
import com.netnote.javaclient.threads.ServerStatusThread;
import com.netnote.javaclient.utils.GoToNote;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
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
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class MainWindow extends Application {
    private TextField usernameField;
    private PasswordField passwordField;
    private static String serverName = "localhost";
    private static int port = 4444;

    // Start() method, javafx application entry point
    @Override
    public void start(Stage stage) throws IOException {
        List<String> args = getParameters().getRaw();
        // Check the arguments and assign them to server name and port
        if (args.size() == 2) {
            serverName = args.get(0);
            port = Integer.parseInt(args.get(1));
        } else if (args.size() == 1) {
            port = Integer.parseInt(args.get(0));
        }
        // Load the user interface from an FXML file
        VBox root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginRegister.fxml")));
        Scene scene = new Scene(root, 600, 390);
        stage.setResizable(false);

        // Set the icon for the stage
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icon.png")));
        stage.getIcons().add(icon);

        // Setting the window title
        stage.setTitle("NetNote Sign in");
        stage.setScene(scene);
        stage.show();

        // Label to show server status
        Label ServerStatus = (Label) scene.lookup("#ServerStatus");

        // main connection to the server
        AtomicReference<Socket> client = new AtomicReference<>();

        // Thread to keep the connection to the server active
        EstablishConnectionThread.run(client, serverName, port);

        // Spawns a new thread to test the connection and report the connection status
        ServerStatusThread.run(serverName, port, ServerStatus);

        // Initialization of input fields and buttons for registration and login
        usernameField = (TextField) scene.lookup("#usernameField");
        passwordField = (PasswordField) scene.lookup("#passwordField");
        Button registerButton = (Button) scene.lookup("#SignUpButton");
        Button loginButton = (Button) scene.lookup("#SignInButton");
        // Action to perform when the register button is pressed
        registerButton.setOnAction(event -> {
            try {
                if (UserManagement.register(usernameField, passwordField, client.get(), Connection.isServerOnline(serverName, port))){
                    // Closing the register window and opening the notes window
                    GoToNote.goToNoteWindow(stage, client.get(), usernameField.getText().toLowerCase());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Action to perform when the login button is pressed
        loginButton.setOnAction(event -> {
            try {
                if (UserManagement.login(usernameField, passwordField, client.get(), Connection.isServerOnline(serverName, port))){
                    GoToNote.goToNoteWindow(stage, client.get(), usernameField.getText().toLowerCase());
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
        launch(args);
    }
}