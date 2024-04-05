package com.example.javaclient;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Connection {
    public static Socket InitConnection(String serverName, int port) throws IOException {
        System.out.println("Connecting to " + serverName + " on port " + port);
        return new Socket(serverName, port);
    }
    public static void sendMsg(String msg, Socket client) {
        System.out.println("message sent: " + msg);
        byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
        OutputStream outToServer = null;
        DataOutputStream out = null;
        try {
            outToServer = client.getOutputStream();
            out = new DataOutputStream(outToServer);
            // Send the bytes to the server
            out.write(bytes);
            out.flush();
        } catch (IOException e) {
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

}
