package com.example.javaclient.notes;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetContent {
    public static String getContent(JSONArray jsonArray, String title) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.has("title") && jsonObject.getString("title").equals(title)) {
                return jsonObject.getString("content");
            }
        }
        return "";
    }
}
