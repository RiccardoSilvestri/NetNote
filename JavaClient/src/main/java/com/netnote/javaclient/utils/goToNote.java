package com.netnote.javaclient.utils;

import com.netnote.javaclient.notes.Note;
import com.netnote.javaclient.threads.EstablishConnectionThread;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class goToNote {
    public static void goToNoteWindow(Stage stage, Socket client, String username) throws IOException {
        stage.close();
        Note note = new Note();
        note.notesWindow(client, username);
        EstablishConnectionThread.terminate();
    }
}
