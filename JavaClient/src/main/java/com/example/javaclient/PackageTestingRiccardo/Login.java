package com.example.javaclient.PackageTestingRiccardo;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Login {
    private static final String SERVER_NAME = "localhost";
    private static final int PORT = 3333;

    public static void login(String name, String password) {
        try (Socket client = new Socket(SERVER_NAME, PORT)) {

            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);

            byte[] bytes = "2".getBytes(StandardCharsets.UTF_8);

            // Send the bytes to the server
            out.write(bytes);
            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);

            System.out.println("Server says " + in.readUTF());

            // Prepare JSON string
            String json = "{\"name\":\"" + name + "\",\"password\":\"" + password + "\"}";
            // Convert the JSON string to bytes
            bytes = json.getBytes(StandardCharsets.UTF_8);

            // Send the bytes to the server
            out.write(bytes);

            // Handle server response if needed
            inFromServer = client.getInputStream();
            in = new DataInputStream(inFromServer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}