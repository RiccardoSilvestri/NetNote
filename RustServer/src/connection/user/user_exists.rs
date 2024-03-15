use serde_json::Value;
use std::fs::File;
use std::io::BufReader;
use std::path::Path;

// returns true if a user with the same username exists in users.json
pub fn user_exists(username: String) -> bool {
    let file_path = Path::new("users.json");
    if !file_path.exists() {
        return true;
    }
    let file = File::open(&file_path).expect("Failed to open file");
    let metadata = std::fs::metadata(&file_path).expect("Unable to read metadata");
    if metadata.len() == 0 {
        return true;
    }
    let reader = BufReader::new(file);
    let users: Value = match serde_json::from_reader(reader) {
        Ok(data) => data,
        Err(_) => return true,
    };

    if let Some(array) = users.as_array() {
        for user in array {
            if let Some(user_name) = user["Name"].as_str() {
                if user_name == username {
                    return true;
                }
            }
        }
    }
    false
}
