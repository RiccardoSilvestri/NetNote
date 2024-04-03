package com.example.javaclient.PackageTestingRiccardo;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SendCredentials {
    private static final String SERVER_NAME = "129.152.5.12";
    private static final int PORT = 4444;

    public static int sendCredentials(Socket client, String option, String name, String password) {
        try {
            int returndelserver;

            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);

            byte[] bytes = option.getBytes(StandardCharsets.UTF_8);
            // Send the bytes to the server
            out.write(bytes);
            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);

            System.out.println("Server says " + in.readUTF());

            String json = "{\"name\":\"" + name + "\",\"password\":\"" + password + "\"}";
            // Convert the JSON string to bytes
            bytes = json.getBytes(StandardCharsets.UTF_8);
            // Send the bytes to the server
            out.write(bytes);
            // Handle server response if needed
            inFromServer = client.getInputStream();
            in = new DataInputStream(inFromServer);
            returndelserver = in.readByte();
            System.out.println(returndelserver);
            return returndelserver;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
