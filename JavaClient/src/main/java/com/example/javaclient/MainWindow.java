package com.example.javaclient;

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
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

public class MainWindow extends Application {
    private TextField usernameField;
    private PasswordField passwordField;
    private static final String SERVER_NAME = "localhost";
    private static final int PORT = 4444;

    private static boolean isServerOnline() {
        try {
            Socket socket = new Socket(SERVER_NAME, PORT);
            socket.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

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

        Label ServerStatus = new Label(" ");
        ServerStatus.setAlignment(Pos.TOP_RIGHT);
        VBox.setMargin(ServerStatus, new Insets(10));
        root.getChildren().add(ServerStatus);


        new Thread(() -> {
            while (true) {
                try {
                    if (isServerOnline()) {
                        Platform.runLater(() -> ServerStatus.setText("ONLINE"));
                        ServerStatus.setTextFill(Color.GREEN);
                    } else {
                        Platform.runLater(() -> ServerStatus.setText("OFFLINE"));
                        ServerStatus.setTextFill(Color.RED);
                    }
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        usernameField = (TextField) scene.lookup("#usernameField");
        passwordField = (PasswordField) scene.lookup("#passwordField");
        Button registerButton = (Button) scene.lookup("#SignUpButton");
        Button loginButton = (Button) scene.lookup("#SignInButton");

        registerButton.setOnAction(event -> {
            try {
                UserManagement.register(usernameField, passwordField, SERVER_NAME, PORT);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        loginButton.setOnAction(event -> {
            try {
                UserManagement.login(usernameField, passwordField, SERVER_NAME, PORT);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}
