package com.netnote.javaclient.utils;

import com.netnote.javaclient.notes.Note;
import com.netnote.javaclient.threads.EstablishConnectionThread;
import com.netnote.javaclient.threads.ServerStatusThread;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class GoToNote {
    public static void goToNoteWindow(Stage stage, Socket client, String username) throws IOException {
        stage.close();
        Note note = new Note();
        note.notesWindow(client, username);
        // terminate threads
        EstablishConnectionThread.terminate();
        ServerStatusThread.terminate();
    }
}
