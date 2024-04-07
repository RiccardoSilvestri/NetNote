package com.example.javaclient.notes;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetContent {

    // Method to obtain the content of a note given the title
    public static String getContent(JSONArray jsonArray, String title) {

        // Iteration through the JSON array of notes
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            // Checking if the current JSON object has a corresponding title
            if (jsonObject.has("title") && jsonObject.getString("title").equals(title)) {

                // Return of the contents of the note
                return jsonObject.getString("content");
            }
        }
        // If the title is not found, returns an empty string
        return "";
    }
}
