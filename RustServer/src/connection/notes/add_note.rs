use std::error::Error;
use std::sync::{Arc, Mutex};
use serde_json::{from_str, Value};
use crate::connection::utils::handle_json::write_json;
use crate::connection::notes::note_exists::note_exists;

pub fn add_note(file_path: &str, target_json: &str, file_access: Arc<Mutex<()>>) -> Result<(), Box<dyn Error>>{
    let value: Value = from_str(target_json)?;
    let author = value["author"].as_str().ok_or("Missing author")?.to_string();
    let title = value["title"].as_str().ok_or("Missing title")?.to_string();
    if note_exists(file_path, &*author, &*title, file_access.clone()){
        return Err(Box::from("Note exists"));
    }
    write_json(target_json.to_string(), file_path, file_access.clone());
    Ok(())
}
