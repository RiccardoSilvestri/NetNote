package com.example.javaclient;

import com.example.javaclient.LoginRegister.Login;
import com.example.javaclient.LoginRegister.Register;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindow extends Application {
    TcpConnectionTesting tcpconnecting = new TcpConnectionTesting();

    Login login = new Login();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("LoginRegister.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 670, 420);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();


        Button registerButton = (Button) scene.lookup("#LoginButton");
        registerButton.setOnAction(event -> TcpConnectionTesting.main());

        // Aggiungi un'azione per gestire il click del pulsante "Login"
        //Button loginButton = (Button) scene.lookup("#loginButton");
        //loginButton.setOnAction(event -> Login.login());
    }

    public static void main(String[] args) {
        launch();
    }
}
