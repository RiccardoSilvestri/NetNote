package com.netnote.javaclient.threads;

import com.netnote.javaclient.Connection;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.net.Socket;

public class ServerStatusThread extends Thread{
    private static volatile boolean isRunning = true;
    public static void terminate(){
        isRunning = false;
    }
    public static void run(String serverName, int port, Label ServerStatus) {
        // Spawns a new thread to test the connection and report the connection status
        new Thread(() -> {
            while (isRunning) {
                try {
                    if (Connection.isServerOnline(serverName, port)) {
                        Platform.runLater(() -> ServerStatus.setText("ONLINE"));
                        ServerStatus.setTextFill(Color.GREEN);
                    } else {
                        Platform.runLater(() -> ServerStatus.setText("OFFLINE"));
                        ServerStatus.setTextFill(Color.RED);
                    }
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Server Status Thread terminated");
        }).start();
    }
}
