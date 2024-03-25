package com.example.javaclient.notes;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class Note {
    public void notes() throws IOException {
        Stage newStage = new Stage();
        VBox newRoot = FXMLLoader.load(getClass().getResource("/com/example/javaclient/Appunti.fxml"));
        Scene newScene = new Scene(newRoot, 920, 920);
        newStage.setScene(newScene);
        newStage.setTitle("New Window");
        newStage.show();

        SendButton(newRoot);

        TextArea noteTextArea = (TextArea) newRoot.lookup("#noteTextArea");
        HBox bottoniHBox = (HBox) newRoot.lookup("#bottoniHBox");
        ImportedNotes(noteTextArea, bottoniHBox);

        Label titoloLabel = (Label) newRoot.lookup("#titoloLabel");
        titoloLabel.setText("Titolo Variabile");
        noteTextArea.setText("benvenuto");

    }

    private static void SendButton(VBox newRoot) {
        HBox sendButtonHBox = new HBox();
        sendButtonHBox.setAlignment(Pos.BOTTOM_RIGHT);
        Button sendButton = new Button("Send");
        sendButtonHBox.getChildren().add(sendButton);
        newRoot.getChildren().add(sendButtonHBox);
    }

    private static void ImportedNotes(TextArea noteTextArea, HBox bottoniHBox) {
        String jsonString = "[{\"Author\":\"Paolo\",\"Content\":\"Oggi sono molto contento\",\"Date\":\"16/11/2002\",\"Title\":\"Nuova\"},{\"Author\":\"Paolo\",\"Content\":\"Lecca lecca\",\"Date\":\"16/11/2002\",\"Title\":\"ruba\"},{\"Author\":\"Paolo\",\"Content\":\"Lello bello\",\"Date\":\"16/11/2002\",\"Title\":\"Rancido\"}]";

        ButtonIncrease(noteTextArea, bottoniHBox, jsonString);
    }
    

    private static void ButtonIncrease(TextArea noteTextArea, HBox bottoniHBox, String jsonString) {
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            int contentCount = 0; // Contatore per i "Content"

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.has("Content")) {
                    contentCount++; // Incrementa il contatore se c'è un "Content"
                }
            }

            Contentviewer(noteTextArea, bottoniHBox, contentCount, jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void Contentviewer(TextArea noteTextArea, HBox bottoniHBox, int contentCount, JSONArray jsonArray) {
        for (int i = 1; i <= contentCount; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i - 1); // Ottieni l'oggetto JSON per il bottone corrente
            String title = jsonObject.getString("Title"); // Ottieni il titolo dal JSON
            Button button = new Button(title); // Crea il bottone con il titolo come testo

            // Aggiungi un EventHandler per gestire la pressione del bottone
            button.setOnAction(event -> {
                String buttonText = ((Button) event.getSource()).getText(); // Ottieni il testo del bottone (il titolo)
                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject noteObject = jsonArray.getJSONObject(j);
                    if (noteObject.getString("Title").equals(buttonText)) {
                        String content = noteObject.getString("Content"); // Ottieni il "Content" corrispondente al titolo
                        noteTextArea.setText(content); // Imposta il testo del TextArea con il "Content" trovato
                        break;
                    }
                }
            });

            bottoniHBox.getChildren().add(button);
        }
    }


}
