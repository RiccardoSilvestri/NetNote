use serde_json::{Value};
use std::fs::{File, OpenOptions};
use std::io::{Read, Write};
use std::error::Error;
use std::sync::{Arc, Mutex};

// Function to delete a note by author and title
pub fn remove_note(file_path: &str, author: &str, title: &str, file_access: Arc<Mutex<()>>) -> Result<(), Box<dyn Error>> {
    // Open the file in read mode
    let mut file = File::open(file_path)?;
    let mut contents = String::new();
    file.read_to_string(&mut contents)?;

    // Parse the JSON data
    let mut json: Value = serde_json::from_str(&contents)?;

    // Check if the JSON is an array
    if let Value::Array(ref mut notes) = json {
        // Iterate over the notes to find the one to delete
        notes.retain(|note| {
            if let Value::Object(note_map) = note {
                // Check if the note has the matching author and title
                if let Some(Value::String(note_author)) = note_map.get("author") {
                    if let Some(Value::String(note_title)) = note_map.get("title") {
                        return !(note_author == author && note_title == title);
                    }
                }
            }
            true // Keep the note if it doesn't match the criteria
        });
    }

    // Open the file in write mode to overwrite it with the updated JSON
    let _guard = file_access.lock().unwrap();
    let mut file = OpenOptions::new().write(true).truncate(true).open(file_path)?;
    // Write the updated JSON back to the file
    file.write_all(json.to_string().as_bytes())?;

    Ok(())
}
