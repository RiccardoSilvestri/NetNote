package src.main.java.com.example.javaclient;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class TcpClient {
    public static void main(String[] args) {
        String serverName = "localhost"; // server IP address
        int port = 3333; // server port number
        Scanner scan = new Scanner(System.in);

        try {
            System.out.println("Connecting to " + serverName + " on port " + port);
            Socket client = new Socket(serverName, port);

            System.out.println("Just connected to " + client.getRemoteSocketAddress());
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);

            String msg = """
                    {
                        "glossary": {
                            "title": "example glossary",
                    		"GlossDiv": {
                                "title": "S",
                    			"GlossList": {
                                    "GlossEntry": {
                                        "ID": "SGML",
                    					"SortAs": "SGML",
                    					"GlossTerm": "Standard Generalized Markup Language",
                    					"Acronym": "SGML",
                    					"Abbrev": "ISO 8879:1986",
                    					"GlossDef": {
                                            "para": "A meta-markup language, used to create markup languages such as DocBook.",
                    						"GlossSeeAlso": ["GML", "XML"]
                                        },
                    					"GlossSee": "markup"
                                    }
                                }
                            }
                        }
                    }
                    """;
            out.write(msg.getBytes());
            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);

            System.out.println("Server says " + in.readUTF());
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
