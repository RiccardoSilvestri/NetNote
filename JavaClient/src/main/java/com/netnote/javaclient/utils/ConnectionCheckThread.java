package com.netnote.javaclient.utils;

import com.netnote.javaclient.Connection;
import com.netnote.javaclient.StartConnection;

import java.net.Socket;
import java.util.concurrent.atomic.AtomicReference;

public class ConnectionCheckThread extends Thread{
    private ThreadController controller;

    public ConnectionCheckThread(ThreadController controller) {
        this.controller = controller;
    }

    public static void run(AtomicReference<Socket> client){
        // Thread to keep the connection to the server active
        new Thread(() -> {
            boolean connected = false;
            while (true) {
                if (!Connection.isServerOnline(client.get().getInetAddress().getHostName(), client.get().getPort()) || !connected) {
                    connected = StartConnection.EstablishConnection(client, client.get().getInetAddress().getHostName(), client.get().getPort());
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
