package com.example.javaclient;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicReference;

public class StartConnection {
    public static boolean EstablishConnection(AtomicReference<Socket> client, String SERVER_NAME, int PORT) {
        try {
            client.set(new Socket(SERVER_NAME, PORT));
            System.out.println("Connected");
            return true;
        } catch (IOException e) {
            System.out.println("Connection failed");
        }
        return false;
    }
}
