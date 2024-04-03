package com.example.javaclient.notes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class Note {
    public void notes(Socket client) throws IOException {
        // Carica il contenuto del file FXML nel VBox
        VBox newRoot = FXMLLoader.load(getClass().getResource("/com/example/javaclient/Appunti.fxml"));

        Stage newStage = new Stage();
        Scene newScene = new Scene(newRoot, 920, 920);
        newStage.setScene(newScene);
        newStage.setTitle("New Window");

        // Crea un Label con il testo desiderato
        Label label = new Label("Benvenuto");

        // Aggiungi il Label al VBox
        newRoot.getChildren().add(0, label); // Aggiungi il label all'inizio del VBox

        // Mostra il nuovo Stage
        newStage.show();

        // Inizializzazione dei componenti dell'interfaccia utente
        TextArea noteTextArea = (TextArea) newRoot.lookup("#noteTextArea");
        noteTextArea.setText("Seleziona una nota per visualizzare il contenuto");

        // Passaggio del controllo alla classe NoteManagement
        NoteManagement noteManagement = new NoteManagement(newRoot);
        noteManagement.initialize(client);
    }
}
