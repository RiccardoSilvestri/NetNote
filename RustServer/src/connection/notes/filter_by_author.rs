use serde_json::{Value};
use std::fs::File;
use std::io::BufReader;
use std::sync::{Arc, Mutex};
use std::error::Error;

// This function filters JSON data by author and returns the filtered data as a JSON string.
pub fn filter_by_author(file_path: &str, author: &str, file_access: Arc<Mutex<()>>) -> Result<String, Box<dyn Error>> {
    // lock the mutex and open the file at the given path.
    let _guard = file_access.lock().unwrap();
    let file = File::open(file_path)?;
    // Create a buffered reader to efficiently read the file's contents.
    let reader = BufReader::new(file);
    // Parse the JSON data from the file into a serde_json::Value.
    let json: Value = serde_json::from_reader(reader)?;

    // Initialize an empty vector to store the filtered JSON objects.
    let mut result = Vec::new();

    // Check if the parsed JSON is an array.
    if let Value::Array(blocks) = json {
        // Iterate over each item in the array.
        for block in blocks {
            // Check if the item is an object.
            if let Value::Object(map) = block {
                // Check if the object has an "author" field that matches the specified author.
                if map.get("Author") == Some(&Value::String(author.to_string())) {
                    // If the author matches, add the object to the result vector.
                    result.push(Value::Object(map));
                }
            }
        }
    }

    // Serialize the result vector to a JSON string.
    let json_string = serde_json::to_string(&result)?;

    // Return the JSON string representation of the filtered data.
    Ok(json_string)
}
