package com.example.javaclient;

import java.io.*;
import java.net.*;

public class TcpClient {
    public static void main(String[] args) {
        String serverName = "localhost"; // replace with your server's IP
        int port = 3333; // replace with your server's port

        try {
            System.out.println("Connecting to " + serverName + " on port " + port);
            Socket client = new Socket(serverName, port);

            System.out.println("Just connected to " + client.getRemoteSocketAddress());
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);

            // replace with your JSON data
            String json = "{\"key\":\"value\"}";
            out.write(json.getBytes());

            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);

            System.out.println("Server says " + in.readUTF());
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
