use std::fmt;
use std::fs::File;
use std::io::BufReader;
use std::path::Path;
use serde_json::{Error, Value};

// returns the password of the user if the user exists in users.json
pub fn get_password_from_file(username: &str, file :&str) -> Result<String, String> {
    let file_path = Path::new(file);
    if !file_path.exists() {
        return Err("File does not exist".to_string());
    }
    let file = match File::open(&file_path) {
        Ok(file) => file,
        Err(_) => return Err("Failed to open file".to_string()),
    };
    let metadata = match std::fs::metadata(&file_path) {
        Ok(metadata) => metadata,
        Err(_) => return Err("Unable to read metadata".to_string()),
    };
    if metadata.len() == 0 {
        return Err("File is empty".to_string());
    }
    let reader = BufReader::new(file);
    let users: Value = match serde_json::from_reader(reader) {
        Ok(data) => data,
        Err(_) => return Err("Failed to parse JSON".to_string()),
    };

    if let Some(array) = users.as_array() {
        for user in array {
            if let Some(user_name) = user["name"].as_str() {
                if user_name.eq_ignore_ascii_case(username) {
                    return if let Some(password) = user["password"].as_str() {
                        println!("{}", password);
                        Ok(password.to_string())
                    } else {
                        Err("Password not found".to_string())
                    }
                }
            }
        }
    }
    Err("User does not exist".to_string())
}


pub fn get_value_from_json(key: &str, target_json: &str) -> Result<String, Box<CustomError>> {
    // Try to parse the string of data into serde_json::Value
    let value: Value = match serde_json::from_str(target_json) {
        Ok(v) => v,
        Err(_) => return Err(Box::new(CustomError { message: "Invalid json".to_string() })),
    };

    // Get the value of the target key as a &str
    if let Some(author) = value[key].as_str() {
        Ok(author.to_string())
    } else {
        Err(Box::new(CustomError { message: "Invalid json: missing author".to_string() }))
    }
}


// Define your custom error type
pub struct CustomError {
    pub message: String,
}

impl fmt::Display for CustomError {
    fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
        write!(f, "{}", self.message)
    }
}

impl fmt::Debug for CustomError {
    fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
        write!(f, "{}", self.message)
    }
}

impl std::error::Error for CustomError {}

#[derive(Debug)]
pub enum JsonCustomError {
    InvalidJson(String),
    SerdeJson(Error),
}

impl fmt::Display for JsonCustomError {
    fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
        match self {
            JsonCustomError::InvalidJson(msg) => write!(f, "Invalid JSON: {}", msg),
            JsonCustomError::SerdeJson(err) => write!(f, "Serde JSON Error: {}", err),
        }
    }
}

impl From<Error> for JsonCustomError {
    fn from(err: Error) -> JsonCustomError {
        JsonCustomError::SerdeJson(err)
    }
}
