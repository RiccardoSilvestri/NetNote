package com.example.javaclient.notes;

import static com.example.javaclient.Connection.readStr;
import static com.example.javaclient.Connection.sendMsg;

import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import com.example.javaclient.utils.NoteToJson;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane; // Importa FlowPane
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

public class NoteManagement {
    private final VBox root;

    public NoteManagement(VBox root) {
        this.root = root;
    }

    // Method for NoteManagement initialization
    public void initialize(Socket client, VBox newRoot, Stage newStage, String user) throws IOException {
        // Method for managing notes buttons
        ManagementButtons(newRoot, user, client, user, newStage);

        // Method to import notes from the server
        ImportNotes(client, newStage);

        // Method to add a new "New Note" button
        NewButton(newRoot, newStage, user, client);
    }

    // LIVE selected note title
    String currenttitle = "";

    // Method to add a new "New Note" button
    private void NewButton(VBox newRoot, Stage newStage, String user, Socket client) {
        Button newButton = new Button("New Note");
        newButton.setOnAction(event -> {

            // Creating a dialog to insert the new note title
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("New Note");
            dialog.setHeaderText("enter the title of your new note:");
            dialog.setContentText("title:");
            Optional<String> result = dialog.showAndWait();

            result.ifPresent(note -> {
                TextArea noteTextArea = (TextArea) newRoot.lookup("#noteTextArea");
                noteTextArea.setText("");
                String textAreaContent;
                if (note.isEmpty()) {

                    // Warning if the title is empty
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Empty title!");
                    alert.setHeaderText("Empty title");
                    alert.setContentText("you can't enter a empty title");
                    alert.showAndWait();
                } else {

                    // Setting the current title and sending the new note to the server
                    currenttitle = note;
                    newStage.setTitle(currenttitle);
                    newStage.show();
                    sendMsg("1", client);
                    System.out.println(readStr(client));
                    textAreaContent = "";
                    String strDate = "";
                    sendMsg(NoteToJson.noteToJson(user, currenttitle, textAreaContent, strDate), client);
                    // read the output of the server, in order not to send multiple messages at once
                    readStr(client);
                    try {
                        ImportNotes(client, newStage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        });
        // Create a hbox to place the "New Note" button on the """top right"""
        FlowPane topFlowPane = new FlowPane(); // modify HBox to FlowPane
        topFlowPane.setAlignment(Pos.BOTTOM_RIGHT);
        topFlowPane.setPadding(new Insets(7, 20, 0, 0));
        topFlowPane.getChildren().add(newButton);
        root.getChildren().add(1, topFlowPane);

    }


    // Method to manage note sending and deletion buttons
    private void ManagementButtons(VBox newRoot, String user, Socket client, String author, Stage newStage) {
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

        // Action to send a note to the server
        sendButton.setOnAction(event -> {
            TextArea noteTextArea = (TextArea) newRoot.lookup("#noteTextArea");
            String textAreaContent = noteTextArea.getText();

            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm");
            String strDate = dateFormat.format(date);

            // Creating and sending the JSON object representing the note to the server
            System.out.println("Testo: " + textAreaContent);
            System.out.println("Titolo: " + currenttitle);
            System.out.println("Data: " + strDate);
            JSONObject json = new JSONObject();
            json.put("author", author);
            json.put("content", textAreaContent);
            json.put("title", currenttitle);
            json.put("date", date.toString());
            System.out.println(NoteToJson.noteToJson(user, currenttitle, textAreaContent, strDate));

            if (currenttitle.equals("") || textAreaContent.equals("")) {
                // Warning if note title or content is empty
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Empty field!");
                alert.setHeaderText("Empty field");
                alert.setContentText("you can't enter a empty field");
                alert.showAndWait();
            } else {

                // Sending the note to the server
                sendMsg("3", client);
                System.out.println(readStr(client));
                sendMsg(json.toString(), client);
                readStr(client);
                try {
                    ImportNotes(client, newStage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // Action to delete a note
        deleteNoteButton.setOnAction(event -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setHeaderText("Are you sure?");
            alert.setContentText("your note cannot be recovered.");

            ButtonType yesButtonType = new ButtonType("Yes");
            ButtonType noButtonType =
                    new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(yesButtonType, noButtonType);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == yesButtonType) {
                // Sending the note deletion request to the server
                sendMsg("2", client);
                System.out.println(readStr(client));
                sendMsg(NoteToJson.noteToJson(user, currenttitle, author, currenttitle), client);
                readStr(client);
                try {
                    ImportNotes(client, newStage);
                    currenttitle = "";
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
            }
        });
    }

    // Method to import notes from the server
    private void ImportNotes(Socket client, Stage newStage) throws IOException {
        TextArea noteTextArea = (TextArea) root.lookup("#noteTextArea");
        FlowPane bottoniFlowPane = (FlowPane) root.lookup("#bottoniFlowPane"); // modify HBox to FlowPane
        sendMsg("0", client);
        String jsonString = readStr(client);
        System.out.println(jsonString);
        ButtonIncrease(noteTextArea, bottoniFlowPane, jsonString, newStage); // modify HBox to FlowPane
    }


    // Method to add note buttons to the view
    private void ButtonIncrease(TextArea noteTextArea, FlowPane bottoniFlowPane, String jsonString, Stage newStage) {
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            int contentCount = 0;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.has("content")) {
                    contentCount++;
                }
            }

            Contentviewer(noteTextArea, bottoniFlowPane, contentCount, jsonArray, newStage); // modify HBox to FlowPane
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Variable to keep track of the current note title
    private String currentNote = null;

    private String originalContent = null;

    // Add this line at the class level
    private String previousTitle = null;

    // Method to display the contents of a selected note
    private void Contentviewer(TextArea noteTextArea, FlowPane bottoniFlowPane, int contentCount, JSONArray jsonArray, Stage newStage) { // modify HBox to FlowPane
        // boolean to remember if the note has just been updated
        AtomicBoolean updatedNote = new AtomicBoolean(true);
        bottoniFlowPane.getChildren().clear();
        for (int i = 1; i <= contentCount; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i - 1);
            String title = jsonObject.getString("title");
            Button button = new Button(title);

            button.setOnAction(event -> {
                String buttonText = ((Button) event.getSource()).getText();
                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject noteObject = jsonArray.getJSONObject(j);
                    if (noteObject.getString("title").equals(buttonText)) {
                        currenttitle = noteObject.getString("title");
                        newStage.setTitle(currenttitle);
                        newStage.show();
                        String currentContent = noteTextArea.getText();

                        // If the current note has been modified, show the confirmation alert
                        if (currentNote != null && !originalContent.equals(currentContent) && !updatedNote.get()) {
                            Alert alert = new Alert(AlertType.CONFIRMATION);
                            alert.setTitle("unsaved data will not be recoverable!");
                            alert.setHeaderText("Are you sure?");
                            alert.setContentText("your note cannot be recovered.");

                            ButtonType yesButtonType = new ButtonType("Yes");
                            ButtonType noButtonType = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                            alert.getButtonTypes().setAll(yesButtonType, noButtonType);
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == yesButtonType) {

                                // Update the current note and its original contents
                                if (previousTitle != null) {
                                    String previousContent =
                                            GetContent.getContent(jsonArray, previousTitle);
                                    noteTextArea.setText(previousContent);
                                }
                            } else {
                                break;
                            }
                        }
                        // the note has been updated
                        updatedNote.set(false);

                        // Update the current note and its original content
                        previousTitle = currentNote;
                        currentNote = buttonText;
                        originalContent = GetContent.getContent(jsonArray, buttonText);

                        noteTextArea.setText(originalContent);
                        break;
                    }
                }
            });
            bottoniFlowPane.getChildren().add(button); // modify HBox to FlowPane
        }
    }
}
