package com.netnote.javaclient.buttons;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Objects;

public class SaveButton {
    public static Button saveButton(){
        Button sendButton = new Button();
        Image saveImg = new Image(Objects.requireNonNull(SaveButton.class.getResourceAsStream("/saveIcon.png")));
        ImageView imageView = new ImageView(saveImg);

        // Set the ImageView size to match the Button size
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        imageView.setPreserveRatio(true);

        Text buttonText = new Text(" Save");
        buttonText.setFill(Color.WHITE);
        HBox hbox = new HBox(imageView, buttonText);

        // Center the HBox inside the Button
        hbox.setAlignment(Pos.CENTER);

        sendButton.setGraphic(hbox);
        sendButton.setMaxWidth(Double.MAX_VALUE);

        return sendButton;
    }
}
