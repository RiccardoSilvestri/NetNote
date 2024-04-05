use std::fs::{File, OpenOptions};
use std::io::{Read, Write};
use std::sync::{Arc, Mutex};
use std::path::Path;
use serde_json::{Error as JsonError, Value};

pub fn write_json(json_string: String, filename: &str, file_access: Arc<Mutex<()>>) -> String {
    eprintln!("{}", json_string);
    let json_result: Result<Value, JsonError> = serde_json::from_str(&json_string);
    return match json_result {
        Ok(json) => {
            // Lock the Mutex before accessing the file
            let _guard = file_access.lock().unwrap();

            // Check if the file exists, if not create it
            if !Path::new(filename).exists() {
                File::create(filename).expect("Failed to create file");
            }

            // Open the file in read mode
            let mut file = OpenOptions::new().read(true).open(filename).expect("Failed to open file");

            // Read the existing JSON data
            let mut contents = String::new();
            file.read_to_string(&mut contents).expect("Failed to read file");

            // Parse the existing JSON data into an array
            let mut array: Vec<Value> = serde_json::from_str(&contents).unwrap_or_else(|_| Vec::new());

            // Append the new JSON data to the array
            array.push(json);

            // Close the file after reading
            drop(file);

            // Reopen the file in write mode
            let mut file = OpenOptions::new().write(true).truncate(true).open(filename).expect("Failed to open file");

            // Write the array back to the file in a pretty-printed format
            writeln!(file, "{}", serde_json::to_string_pretty(&array).expect("Failed to serialize JSON")).expect("Failed to write to file");
            println!("Wrote to json");
            let response = serde_json::to_string_pretty(&array.last().unwrap()).expect("Failed to serialize JSON");
            response
        }
        Err(err) => {
            eprintln!("Failed to parse JSON: {}", err);
            eprintln!("{}", json_string);
            "Error".to_string()
        }
    }
}
