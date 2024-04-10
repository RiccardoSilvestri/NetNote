package com.netnote.javaclient.threads;

import com.netnote.javaclient.Connection;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.net.Socket;

public class ServerStatusThread extends Thread{
    public static void run(String serverName, int port, Label ServerStatus) {
        // Spawns a new thread to test the connection and report the connection status
        new Thread(() -> {
            while (true) {
                try {
                    if (Connection.isServerOnline(serverName, port)) {
                        Platform.runLater(() -> ServerStatus.setText("ONLINE"));
                        ServerStatus.setTextFill(Color.GREEN);
                    } else {
                        Platform.runLater(() -> ServerStatus.setText("OFFLINE"));
                        ServerStatus.setTextFill(Color.RED);
                    }
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
