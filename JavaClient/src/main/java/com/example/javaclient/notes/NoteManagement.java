package com.example.javaclient.notes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert.AlertType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Optional;

public class NoteManagement {
    private VBox root;

    public NoteManagement(VBox root) {
        this.root = root;
    }

    public void initialize() {
        ManagementButtons();
        ImportedNotes();
    }

    private void ManagementButtons() {
        VBox buttonVBox = new VBox();
        buttonVBox.setAlignment(Pos.BOTTOM_CENTER);
        buttonVBox.setSpacing(10);
        buttonVBox.setPadding(new Insets(0, 20, 10, 20));

        Button sendButton = new Button("Send");
        sendButton.setMaxWidth(Double.MAX_VALUE);
        Button deleteNoteButton = new Button("Delete Note");
        deleteNoteButton.setMaxWidth(Double.MAX_VALUE);

        buttonVBox.getChildren().addAll(sendButton, deleteNoteButton);
        root.getChildren().add(buttonVBox);

        sendButton.setOnAction(event -> {

        });

        deleteNoteButton.setOnAction(event -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setHeaderText("Are you sure?");
            alert.setContentText("your note cannot be recovered.");

            ButtonType yesButtonType = new ButtonType("Yes");
            ButtonType noButtonType = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(yesButtonType, noButtonType);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == yesButtonType) {
                System.out.println("Deleted Note");
            } else {
            }
        });
    }

    private void ImportedNotes() {
        TextArea noteTextArea = (TextArea) root.lookup("#noteTextArea");
        HBox bottoniHBox = (HBox) root.lookup("#bottoniHBox");

        String jsonString = "[{\"Author\":\"Paolo\",\"Content\":\"Oggi sono molto contento\",\"Date\":\"16/11/2002\",\"Title\":\"Nuova\"},{\"Author\":\"Paolo\",\"Content\":\"Lecca lecca\",\"Date\":\"16/11/2002\",\"Title\":\"ruba\"},{\"Author\":\"Paolo\",\"Content\":\"Lello bello\",\"Date\":\"16/11/2002\",\"Title\":\"Rancido\"}]";

        ButtonIncrease(noteTextArea, bottoniHBox, jsonString);
    }

    private void ButtonIncrease(TextArea noteTextArea, HBox bottoniHBox, String jsonString) {
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            int contentCount = 0;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.has("Content")) {
                    contentCount++;
                }
            }

            Contentviewer(noteTextArea, bottoniHBox, contentCount, jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Contentviewer(TextArea noteTextArea, HBox bottoniHBox, int contentCount, JSONArray jsonArray) {
        for (int i = 1; i <= contentCount; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i - 1);
            String title = jsonObject.getString("Title");
            Button button = new Button(title);

            button.setOnAction(event -> {
                String buttonText = ((Button) event.getSource()).getText();
                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject noteObject = jsonArray.getJSONObject(j);
                    if (noteObject.getString("Title").equals(buttonText)) {
                        String content = noteObject.getString("Content");
                        noteTextArea.setText(content);
                        break;
                    }
                }
            });

            bottoniHBox.getChildren().add(button);
        }
    }
}
