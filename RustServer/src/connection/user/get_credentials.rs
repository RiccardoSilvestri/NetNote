use std::fmt;
use std::fs::File;
use std::io::BufReader;
use std::path::Path;
use serde_json::{Error, Value};

// returns the password of the user if the user exists in users.json
pub fn get_password_from_file(username: &str, file :&str) -> String {
    let file_path = Path::new(file);
    if !file_path.exists() {
        return "File does not exist".to_string();
    }
    let file = File::open(&file_path).expect("Failed to open file");
    let metadata = std::fs::metadata(&file_path).expect("Unable to read metadata");
    if metadata.len() == 0 {
        return "File is empty".to_string();
    }
    let reader = BufReader::new(file);
    let users: Value = match serde_json::from_reader(reader) {
        Ok(data) => data,
        Err(_) => return "Failed to parse JSON".to_string(),
    };

    if let Some(array) = users.as_array() {
        for user in array {
            if let Some(user_name) = user["Name"].as_str() {
                if user_name.eq_ignore_ascii_case(username) {
                    return user["Password"].as_str().unwrap_or_else(|| "Password not found").to_string();
                }
            }
        }
    }
    "User does not exist".to_string()
}

#[derive(Debug)]
pub enum CustomError {
    InvalidJson(String),
    SerdeJson(Error),
}

impl fmt::Display for CustomError {
    fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
        match self {
            CustomError::InvalidJson(msg) => write!(f, "Invalid JSON: {}", msg),
            CustomError::SerdeJson(err) => write!(f, "Serde JSON Error: {}", err),
        }
    }
}

impl From<Error> for CustomError {
    fn from(err: Error) -> CustomError {
        CustomError::SerdeJson(err)
    }
}