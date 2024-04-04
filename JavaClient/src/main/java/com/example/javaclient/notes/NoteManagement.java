package com.example.javaclient.notes;
import com.example.javaclient.Connection;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import javafx.scene.control.TextArea;
import static jdk.internal.agent.Agent.getText;

public class NoteManagement {
    private VBox root;
    public NoteManagement(VBox root) {
        this.root = root;
    }
    public void initialize(Socket client, VBox newRoot, Stage newStage) throws IOException {
        ManagementButtons(newRoot);
        ImportNotes(client,newStage);
        NewButton(newRoot);
    }
    String currenttitle = "";

    private static String noteToJson(String author, String title, String content, String date){
        return "{"
                + "\"author\": \"" + author + "\","
                + "\"content\": \"" + content + "\","
                + "\"title\": \"" + title + "\","
                + "\"date\": \"" + date + "\""
                + "}";
    }

    private void NewButton(VBox newRoot) {
        Button newButton = new Button("New Note");
        newButton.setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("New Note");
            dialog.setHeaderText("enter the title of your new note:");
            dialog.setContentText("title:");
            Optional<String> result = dialog.showAndWait();

            result.ifPresent(note -> {
                TextArea noteTextArea = (TextArea) newRoot.lookup("#noteTextArea");
                noteTextArea.setText(" ");
                String textAreaContent = noteTextArea.getText();
                currenttitle = note;
//                Date date = Calendar.getInstance().getTime();
//                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm");
//                String strDate = dateFormat.format(date);
//                System.out.println("Testo: " +textAreaContent);
//                System.out.println("Titolo: " + currenttitle);
//                System.out.println("Data: "+ strDate);
            });
        });

        HBox topHBox = new HBox();
        topHBox.setAlignment(Pos.TOP_RIGHT);
        topHBox.setPadding(new Insets(20, 20, 0, 0));
        topHBox.getChildren().add(newButton);
        root.getChildren().add(0, topHBox);
    }
    private void ManagementButtons(VBox newRoot) {
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
            TextArea noteTextArea = (TextArea) newRoot.lookup("#noteTextArea");
            String textAreaContent = noteTextArea.getText();

            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm");
            String strDate = dateFormat.format(date);

            System.out.println("Testo: " +textAreaContent);
            System.out.println("Titolo: " + currenttitle);
            System.out.println("Data: "+ strDate);
            String author="DAPASSARE";
            System.out.println(noteToJson(author,currenttitle,textAreaContent,strDate));
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
            }else {
            }
        });
    }

    private void ImportNotes(Socket client,Stage newStage) throws IOException {
        TextArea noteTextArea = (TextArea) root.lookup("#noteTextArea");
        HBox bottoniHBox = (HBox) root.lookup("#bottoniHBox");
        Connection.sendMsg("0", client);
        String jsonString = Connection.readStr(client);
        System.out.println(jsonString);
        ButtonIncrease(noteTextArea, bottoniHBox, jsonString,newStage);
    }

    private void ButtonIncrease(TextArea noteTextArea, HBox bottoniHBox, String jsonString,Stage newStage) {
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            int contentCount = 0;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.has("content")) {
                    contentCount++;
                }
            }

            Contentviewer(noteTextArea, bottoniHBox, contentCount, jsonArray,newStage);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void Contentviewer(TextArea noteTextArea, HBox bottoniHBox, int contentCount, JSONArray jsonArray,Stage newStage) {
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
                        //TODO: aggiornare il titolo
                        newStage.setTitle(currenttitle);
                        newStage.show();
                        String content = noteObject.getString("content");
                        noteTextArea.setText(content);
                        break;
                    }
                }
            });
            bottoniHBox.getChildren().add(button);
        }
    }
}
