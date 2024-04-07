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
    public void notes(Socket client, String user) throws IOException {
        // Loading the note layout from the FXML
        VBox newRoot = FXMLLoader.load(getClass().getResource("/com/example/javaclient/Appunti.fxml"));

        // Creating a new note window
        Stage newStage = new Stage();
        Scene newScene = new Scene(newRoot, 700, 500);
        newStage.setScene(newScene);
        newStage.setTitle("New Window");

        // Adding a personalized label to the user
        Label label = new Label("Benvenuto " + user + "!");
        newRoot.getChildren().add(0, label);
        newStage.show();

        // take the textarea for notes from the layout
        TextArea noteTextArea = (TextArea) newRoot.lookup("#noteTextArea");
        noteTextArea.setText("Seleziona una nota per visualizzare il contenuto");

        // Initialization of NoteManagement
        NoteManagement noteManagement = new NoteManagement(newRoot);
        noteManagement.initialize(client,newRoot,newStage, user);
    }




}
