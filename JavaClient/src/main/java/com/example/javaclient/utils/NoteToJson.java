package com.example.javaclient.utils;

public class NoteToJson {
    public static String noteToJson(String author, String title, String content, String date) {
        return "{" +
                "\"author\": \"" + author + "\"," +
                "\"content\": \"" + content + "\"," +
                "\"title\": \"" + title + "\"," +
                "\"date\": \"" + date + "\"" +
                "}";
    }
}
