package com.example.javaclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Connection {
    public static Socket InitConnection(String serverName, int port) throws IOException {
        System.out.println("Connecting to " + serverName + " on port " + port);
        return new Socket(serverName, port);
    }
    private static void sendMsg(String msg, DataOutputStream out) throws IOException {
        byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);

        // Send the bytes to the server
        out.write(bytes);
    }
    private static String readStr(Socket client) throws IOException {
        InputStream inFromServer = client.getInputStream();
        DataInputStream in = new DataInputStream(inFromServer);
        return in.readUTF();
    }
}
