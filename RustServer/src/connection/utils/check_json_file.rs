use std::fs;
use serde_json::{Value, Error};

pub fn check_json_file(file_path: &str) -> Result<bool, Error> {
    let content = fs::read_to_string(file_path).map_err(|e| {
        // Convert the std::io::Error to a serde_json::Error
        Error::io(e)
    })?;

    let _: Value = serde_json::from_str(&content)?;
    Ok(true)
}
