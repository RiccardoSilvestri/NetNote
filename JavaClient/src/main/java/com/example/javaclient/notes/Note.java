package com.example.javaclient.notes;

import java.util.Objects;
import java.util.Scanner;

public class Note {
    String title, author, date, content;

    public Note(String title, String author, String date, String content) {
        this.title = title;
        this.author = author;
        this.date = date;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(title, note.title) && Objects.equals(author, note.author) && Objects.equals(date, note.date) && Objects.equals(content, note.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, date, content);
    }

    public static String noteToJson(Note note){
        return  "{"
                + "\"Title\": \"" + note.title + "\","
                + "\"Author\": \"" + note.author + "\","
                + "\"Date\": \"" + note.date + "\","
                + "\"Content\": \"" + note.content + "\""
                + "}";
    }
    public static Note inputNote(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Title: ");
        String title = scan.nextLine();
        System.out.print("Author: ");
        String author = scan.nextLine();
        System.out.print("Date: ");
        String date = scan.nextLine();
        System.out.print("Content: ");
        String content = scan.nextLine();
        return new Note(title, author, date, content);
    }
}
