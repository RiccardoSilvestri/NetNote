package com.example.javaclient;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Connection {

    // Method to initialize the connection to the server
    public static Socket InitConnection(String serverName, int port) throws IOException {
        System.out.println("Connecting to " + serverName + " on port " + port);
        return new Socket(serverName, port);
    }

    // Method to send a message to the server
    public static void sendMsg(String msg, Socket client) {
        System.out.println("message sent: " + msg); // Console printing to indicate message sending
        byte[] bytes = msg.getBytes(StandardCharsets.UTF_8); // Convert the message to bytes
        OutputStream outToServer = null;
        DataOutputStream out = null;
        try {

            // Get socket stream output and Send bytes to server
            outToServer = client.getOutputStream();
            out = new DataOutputStream(outToServer);
            // Send the bytes to the server
            out.write(bytes);
            out.flush();
        } catch (IOException e) {
            // Handling exceptions in case of error in sending the message
            System.out.println("An error occurred while sending the message: " + e.getMessage());
        }
    }


    public static String readStr(Socket client) {
        try {
            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            return in.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    // Method to check the online server status
    public static boolean isServerOnline(String SERVER_NAME, int PORT) {
        try {
            // Try to create a connection to the server
            Socket socket = new Socket(SERVER_NAME, PORT);
            socket.close();
            return true;//true is if it successfully
        } catch (IOException e) {
            return false; //false is if it failed
        }
    }

}
