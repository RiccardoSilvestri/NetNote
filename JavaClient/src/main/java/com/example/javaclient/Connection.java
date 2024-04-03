package com.example.javaclient;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Connection {
    public static Socket InitConnection(String serverName, int port) throws IOException {
        System.out.println("Connecting to " + serverName + " on port " + port);
        return new Socket(serverName, port);
    }
    public static void sendMsg(String msg, Socket client) throws IOException {
        byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
        OutputStream outToServer = client.getOutputStream();
        DataOutputStream out = new DataOutputStream(outToServer);
        // Send the bytes to the server
        out.write(bytes);
    }
    public static String readStr(Socket client) throws IOException {
        InputStream inFromServer = client.getInputStream();
        DataInputStream in = new DataInputStream(inFromServer);
        return in.readUTF();
    }
}
