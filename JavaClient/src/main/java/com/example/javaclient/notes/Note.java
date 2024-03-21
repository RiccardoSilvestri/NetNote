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
        ImportedNotes(noteTextArea);


        // Ottieni riferimenti al Label e all'HBox
        Label titoloLabel = (Label) newRoot.lookup("#titoloLabel");
        HBox bottoniHBox = (HBox) newRoot.lookup("#bottoniHBox");

        // Imposta il titolo
        titoloLabel.setText("Titolo Variabile");

        // Aggiungi bottoni all'HBox
        for (int i = 1; i <= 9; i++) {
            Button button = new Button(String.valueOf(i));
            bottoniHBox.getChildren().add(button);
        }

    }


    private static void SendButton(VBox newRoot) {
        HBox sendButtonHBox = new HBox();
        sendButtonHBox.setAlignment(Pos.BOTTOM_RIGHT);
        Button sendButton = new Button("Send");
        sendButtonHBox.getChildren().add(sendButton);
        newRoot.getChildren().add(sendButtonHBox);
    }


    private static void ImportedNotes(TextArea noteTextArea) {
        String jsonString = "[{\"Author\":\"Paolo\",\"Content\":\"Oggi sono molto contento\",\"Date\":\"16/11/2002\",\"Title\":\"Nuova\"}]";

        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            String content = jsonObject.getString("Content");

            noteTextArea.setText(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
