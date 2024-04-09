package com.netnote.javaclient.notes;

import com.netnote.javaclient.Connection;
import com.netnote.javaclient.StartConnection;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class ConnectionThread extends Thread {
    public static void run(Socket client) {
        // spawn a thread that displays an error message when not connected to the server
        AtomicReference<Socket> atomicClient = new AtomicReference<>();
        atomicClient.set(client);
        new Thread(() -> {
            boolean connected = false;
            AtomicBoolean running = new AtomicBoolean(true);
            while (running.get()) {
                if (!Connection.isServerOnline(client.getInetAddress().getHostName(), client.getPort()) || !connected) {
                    connected = StartConnection.EstablishConnection(atomicClient, client.getInetAddress().getHostName(), client.getPort());
                    System.out.println("Connected: " + connected);
                    if (!connected) {
                        Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("CONNECTION FAILED!");
                            alert.setHeaderText("You're not connected to the server");
                            alert.setContentText("You will be logged out");
                            running.set(false);
                            alert.showAndWait();
                            // Exit the current JavaFX application
                            Platform.exit();
                            System.exit(0);
                        });
                    }
                }
                try {
                    Thread.sleep(5000); // Moved outside the condition
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}

