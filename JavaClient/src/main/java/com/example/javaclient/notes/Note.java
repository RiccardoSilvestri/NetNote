package com.example.javaclient.notes;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

import static jdk.internal.agent.Agent.getText;

public class Note {
    public void notes(Socket client, String user) throws IOException {
        // Loading the note layout from the FXML
        VBox newRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclient/Appunti.fxml")));

        // Creating a new note window
        Stage noteStage = new Stage();
        Scene newScene = new Scene(newRoot, 700, 500);
        noteStage.setScene(newScene);
        noteStage.setTitle("New Window");

        // Set the icon for the stage
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icon.png")));
        noteStage.getIcons().add(icon);

        // Adding a personalized label to the user
        Label label = new Label("Benvenuto " + user + "!");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        newRoot.getChildren().add(0, label);


        ImageView bannerImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/javaclient/banner.png"))));
        bannerImageView.setFitWidth(199);
        bannerImageView.setFitHeight(72);
        newRoot.getChildren().add(0, bannerImageView);
        noteStage.show();

        // take the textarea for notes from the layout
        TextArea noteTextArea = (TextArea) newRoot.lookup("#noteTextArea");
        noteTextArea.setText("Seleziona una nota per visualizzare il contenuto");

        // Initialization of NoteManagement
        NoteManagement noteManagement = new NoteManagement(newRoot);
        noteManagement.initialize(client,newRoot,noteStage, user);

        // kill all threads when closing stage
        noteStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
    }




}
