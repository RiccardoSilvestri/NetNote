package com.example.javaclient.notes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

import static jdk.internal.agent.Agent.getText;

public class Note {
    public void notes(Socket client) throws IOException {
        // Carica il contenuto del file FXML nel VBox
        VBox newRoot = FXMLLoader.load(getClass().getResource("/com/example/javaclient/Appunti.fxml"));

        Stage newStage = new Stage();
        Scene newScene = new Scene(newRoot, 700, 500);
        newStage.setScene(newScene);
        newStage.setTitle("New Window");

        Label label = new Label("Benvenuto");


        newRoot.getChildren().add(0, label);

        newStage.show();

        TextArea noteTextArea = (TextArea) newRoot.lookup("#noteTextArea");
        noteTextArea.setText("Seleziona una nota per visualizzare il contenuto");
//       TextAreaContent(noteTextArea);

        NoteManagement noteManagement = new NoteManagement(newRoot);
        noteManagement.initialize(client,newRoot,newStage);
    }


//    public static String TextAreaContent(TextArea noteTextArea) {
//        String text = noteTextArea.getText(); // Ottenere il testo dal TextArea
//        System.out.println(text);
//        return text;
//    }


}
