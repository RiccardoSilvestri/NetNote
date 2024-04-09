package com.netnote.javaclient.utils;

import com.netnote.javaclient.Connection;
import com.netnote.javaclient.StartConnection;

import java.net.Socket;
import java.util.concurrent.atomic.AtomicReference;

public class ConnectionCheckThread extends Thread{
    public static void run(AtomicReference<Socket> client, String serverName, int port){
        // Thread to keep the connection to the server active
        new Thread(() -> {
            boolean connected = false;
            while (true) {
                if (!Connection.isServerOnline(serverName, port) || !connected) {
                    connected = StartConnection.EstablishConnection(client, serverName, port);
                    System.out.println(connected);
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
