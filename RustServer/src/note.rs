use serde::{Serialize, Deserialize};
use std::fs::File;
use std::io::Write;
use std::collections::HashMap;

#[derive(Serialize, Deserialize, Debug)]
struct Note {
    title: String,
    author: String,
    date: String,
    content: String,
}

fn main() {
    let mut notes = HashMap::new();

    let note = Note {
        title: String::from("My Note"),
        author: String::from("Author Name"),
        date: String::from("2024-03-13"),
        content: String::from("This is the content of the note."),
    };

    notes.insert(note.title.clone(), note);

    let serialized_notes = serde_json::to_string(&notes).unwrap();

    let mut file = File::create("notes.json").expect("Unable to create file");
    file.write_all(serialized_notes.as_bytes()).expect("Unable to write data");  // Use expect to handle potential Write errors
}
