use std::fs::File;
use std::io::BufReader;
use std::sync::{Arc, Mutex};
use serde_json::{Value, from_reader};
use crate::connection::utils::check_json_file::check_json_file;

pub fn note_exists(file_path: &str, author: &str, title: &str, file_access: Arc<Mutex<()>>) -> bool {
    // Lock the mutex
    match check_json_file(file_path){
        Ok(_) => { }
        Err(_) => { return false }
    }
    let _guard = file_access.lock().unwrap();
    let file = File::open(file_path).expect("File not found");
    let reader = BufReader::new(file);
    let notes: Vec<Value> = from_reader(reader).expect("Error reading JSON");

    for note in &notes {
        if note["author"].as_str() == Some(author) && note["title"].as_str() == Some(title) {
            return true;
        }
    }
    false
}
