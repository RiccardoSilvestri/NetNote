package com.netnote.javaclient.threads;

import com.netnote.javaclient.Connection;
import com.netnote.javaclient.StartConnection;

import java.net.Socket;
import java.util.concurrent.atomic.AtomicReference;

public class EstablishConnectionThread extends Thread{
    private static volatile boolean isRunning = true;
    public static void terminate(){
        isRunning = false;
    }
    public static void run(AtomicReference<Socket> client, String serverName, int port){
        // Thread to keep the connection to the server active
        new Thread(() -> {
            boolean connected = false;
            while (isRunning) {
                if (!Connection.isServerOnline(serverName, port) || !connected)
                    connected = StartConnection.EstablishConnection(client, serverName, port);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Thread stopped");
        }).start();
    }
}
