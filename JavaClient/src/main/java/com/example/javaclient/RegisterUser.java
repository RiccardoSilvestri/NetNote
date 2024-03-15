package com.example.javaclient;

import com.example.javaclient.notes.Note;
import com.example.javaclient.notes.User;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;


public class RegisterUser {
    public static void main(String[] args) {
        String serverName = "localhost"; // or IP address of server
        int port = 3333;
        try {
            System.out.println("Connecting to " + serverName + " on port " + port);
            Socket client = new Socket(serverName, port);

            System.out.println("Just connected to " + client.getRemoteSocketAddress());
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);

            // Convert the JSON string to bytes
            byte[] bytes = "1".getBytes(StandardCharsets.UTF_8);

            // Send the bytes to the server
            out.write(bytes);
            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);

            System.out.println("Server says " + in.readUTF());

            User user = new User("Paolo", "cij3n4al");
            // Sample JSON string
            String json = User.UserToJson(user);

            // Convert the JSON string to bytes
            bytes = json.getBytes(StandardCharsets.UTF_8);

            // Send the bytes to the server
            out.write(bytes);

            inFromServer = client.getInputStream();
            in = new DataInputStream(inFromServer);

            System.out.println("Server says " + in.readUTF());
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}