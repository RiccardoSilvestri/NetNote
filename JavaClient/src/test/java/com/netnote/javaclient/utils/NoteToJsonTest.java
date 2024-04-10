package com.netnote.javaclient.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteToJsonTest {

    @Test
    void noteToJson() {
        String author = "Francesco Lanza";
        String title = "Test Note";
        String content = "This is a test note.";
        String date = "2023-04-01";

        String expectedJson = "{\"author\": \"Francesco Lanza\",\"content\": \"This is a test note.\",\"title\": \"Test Note\",\"date\": \"2023-04-01\"}";

        String actualJson = NoteToJson.noteToJson(author, title, content, date);

        assertEquals(expectedJson, actualJson, "The noteToJson method should correctly format the note into a JSON string.");
    }
}