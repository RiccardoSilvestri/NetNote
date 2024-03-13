package com.example.javaclient;

import com.example.javaclient.LoginRegister.Login;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TcpConnectionTesting {
    public static void main(String[] args) {

        String serverName = "localhost"; // or IP address of server
        int port = 3333;
        try {
            System.out.println("Connecting to " + serverName + " on port " + port);
            Socket client = new Socket(serverName, port);

            System.out.println("Just connected to " + client.getRemoteSocketAddress());
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);

            // Sample JSON string
            Login login = new Login();
            String nomeInput = login.inserisciNome();
            Login loginP = new Login();
            String nomePassword = login.inserisciPassword();

            String json = nomeInput;
            // Convert the JSON string to bytes
            byte[] bytes = json.getBytes(StandardCharsets.UTF_8);

            // Send the bytes to the server
            out.write(bytes);

            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);

            System.out.println("Server says " + in.readUTF());
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
