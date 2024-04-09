package com.example.javaclient.buttons;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Objects;

public class NewNoteButton {
    public static Button newNoteButton(){
        Button newNoteButton = new Button();
        Image saveImg = new Image(Objects.requireNonNull(SaveButton.class.getResourceAsStream("/newNoteIcon.png")));
        ImageView imageView = new ImageView(saveImg);

        // Set the ImageView size to match the Button size
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        imageView.setPreserveRatio(true);

        HBox hbox = new HBox(imageView);

        // Center the HBox inside the Button
        hbox.setAlignment(Pos.CENTER);

        newNoteButton.setGraphic(hbox);
        newNoteButton.setMaxWidth(Double.MAX_VALUE);

        return newNoteButton;
    }
}
