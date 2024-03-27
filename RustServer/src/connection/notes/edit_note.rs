use std::error::Error;
use std::sync::{Arc, Mutex};
use crate::connection::notes::add_note::add_note;
use crate::connection::notes::remove_note::remove_note;

pub fn edit_note (file_path: &str, target_json: &str, file_access: Arc<Mutex<()>>) -> Result<(), Box<dyn Error>> {
    match remove_note(file_path, target_json, file_access.clone()) {
        Err(e) => { return Err(e) }
        Ok(_) => { add_note(file_path, target_json, file_access.clone()) }
    }.expect("Error");
    Ok(())
}