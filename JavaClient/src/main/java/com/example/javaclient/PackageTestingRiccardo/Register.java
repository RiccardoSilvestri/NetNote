package com.example.javaclient.PackageTestingRiccardo;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Register {
    private static final String SERVER_NAME = "localhost";
    private static final int PORT = 3333;

    public static void register(String username, String password) {
        try (Socket client = new Socket(SERVER_NAME, PORT)) {
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);

            // Prepare JSON string
            String json = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";

            // Convert the JSON string to bytes
            byte[] bytes = json.getBytes(StandardCharsets.UTF_8);

            // Send the bytes to the server
            out.write(bytes);

           // Handle server response if needed
            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            System.out.println("Server says: " + in.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
