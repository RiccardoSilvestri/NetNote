package com.netnote.javaclient.notes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Optional;

class GetContentTest {

    @Test
    void shouldReturnEmptyStringForEmptyJsonArray() {
        JSONArray jsonArray = new JSONArray();
        String titleToFind = "Note 1";
        String result = GetContent.getContent(jsonArray, titleToFind);
        assertEquals("", result);
    }

    @Test
    void shouldHandleDuplicateTitles() {
        JSONArray jsonArray = new JSONArray();
        JSONObject note1 = new JSONObject();
        note1.put("title", "Note 1");
        note1.put("content", "nota 1 content");
        jsonArray.put(note1);

        JSONObject note2 = new JSONObject();
        note2.put("title", "Note 1");
        note2.put("content", "nota 2 content");
        jsonArray.put(note2);

        String titleToFind = "Note 1";
        String result = GetContent.getContent(jsonArray, titleToFind);
        assertEquals("nota 1 content", result); // Assuming the first match is returned
    }

    @Test
    void shouldHandleObjectsWithoutTitleOrContent() {
        JSONArray jsonArray = new JSONArray();
        JSONObject note1 = new JSONObject();
        note1.put("title", "Note 1");
        note1.put("content", "nota 1 content");
        jsonArray.put(note1);

        JSONObject note2 = new JSONObject();
        jsonArray.put(note2);

        String titleToFind = "Note 1";
        String result = GetContent.getContent(jsonArray, titleToFind);
        assertEquals("nota 1 content", result);
    }

}
