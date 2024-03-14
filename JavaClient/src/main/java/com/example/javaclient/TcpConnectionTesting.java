package com.example.javaclient;

import com.example.javaclient.LoginRegister.Login;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TcpConnectionTesting {
    private JTextArea textArea;

    public TcpConnectionTesting() {
        JFrame frame = new JFrame();
        textArea = new JTextArea(24, 80);
        frame.getContentPane().add(new JScrollPane(textArea), "Center");
        frame.pack();
        frame.setVisible(true);
    }

    public void print(String message) {
        textArea.append(message + "\n");
    }

    public static void main() {
        TcpConnectionTesting tcp = new TcpConnectionTesting();

        String serverName = "localhost";
        int port = 3333;
        try {
            tcp.print("Connecting to " + serverName + " on port " + port);
            Socket client = new Socket(serverName, port);

            tcp.print("Just connected to " + client.getRemoteSocketAddress());
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);

            Login login = new Login();
            String nomeInput = login.inserisciNome();
            Login loginP = new Login();
            String nomePassword = login.inserisciPassword();

            // Convert the JSON string to bytes
            byte[] bytes = nomeInput.getBytes(StandardCharsets.UTF_8);
            byte[] bytes2 = nomePassword.getBytes(StandardCharsets.UTF_8);

            // Send the bytes to the server
            out.write(bytes);
            out.write(bytes2);

            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);

            tcp.print("Server says " + in.readUTF());
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
