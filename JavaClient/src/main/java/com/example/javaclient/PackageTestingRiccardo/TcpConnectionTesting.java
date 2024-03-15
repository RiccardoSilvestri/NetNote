package com.example.javaclient.PackageTestingRiccardo;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TcpConnectionTesting {
    private JTextArea textArea;

    public TcpConnectionTesting() {
        // Crea una finestra per mostrare i messaggi
        JFrame frame = new JFrame();
        textArea = new JTextArea(24, 80);
        frame.getContentPane().add(new JScrollPane(textArea), "Center");
        frame.pack();
        frame.setVisible(true);
    }

    // Metodo per stampare i messaggi nella finestra
    public void print(String message) {
        textArea.append(message + "\n");
    }

    // Metodo per avviare la connessione TCP e inviare dati al server
    public void connectAndSendData() {
        String serverName = "localhost";
        int port = 3333;

        try {
            // Connessione al server
            print("Connecting to " + serverName + " on port " + port);
            Socket client = new Socket(serverName, port);

            // Notifica la connessione riuscita
            print("Just connected to " + client.getRemoteSocketAddress());

            // Ottiene i flussi di output per inviare dati al server
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);

            // Simula l'inserimento di nome utente e password
            Login login = new Login();
            String username = login.inserisciNome();
            String password = login.inserisciPassword();

            // Invia nome utente e password al server
            byte[] usernameBytes = username.getBytes(StandardCharsets.UTF_8);
            byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
            out.write(usernameBytes);
            out.write(passwordBytes);

            // Riceve e stampa la risposta del server
            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            print("Server says: " + in.readUTF());

            // Chiude la connessione
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
