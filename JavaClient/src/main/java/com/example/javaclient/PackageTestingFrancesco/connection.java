package com.example.javaclient.PackageTestingFrancesco;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class connection {
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
    private static int readInt(Socket client) throws IOException {
        InputStream inFromServer = client.getInputStream();
        DataInputStream in = new DataInputStream(inFromServer);
        return in.readInt();
    }
    private static String credToJson(String name, String password){
        return "{"
                + "\"name\": \"" + name + "\","
                + "\"password\": \"" + password + "\""
                + "}";
    }
    public static void main(String[] args) {
        String serverName = "localhost"; // or IP address of server
        int port = 4444;
        try {
            System.out.println("Connecting to " + serverName + " on port " + port);
            Socket client = new Socket(serverName, port);

            System.out.println("Just connected to " + client.getRemoteSocketAddress());
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);

            String username, password;
            System.out.println("1) register\n2) login");
            Scanner scan = new Scanner(System.in);
            switch (scan.nextInt()){
                case 1:
                    //login
                    sendMsg("1", out);
                    break;
                case 2:
                    //register
                    sendMsg("2", out);
                    break;
                default:
                    return;
            }
            scan.nextLine();
            readStr(client);
            System.out.println("Insert username and password:");
            username = scan.nextLine();
            password = scan.nextLine();
            String json = credToJson(username, password);
            System.out.println(json);
            sendMsg(json, out);
            int response = in.readByte();
            System.out.println(response);
            // if the login request fails, close
            if (response == 0) {
                client.close();
                return;
            };

            System.out.println("""
                    1) make note
                    2) delete note
                    """);
            // send the request
            sendMsg(scan.nextLine(), out);

            
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}