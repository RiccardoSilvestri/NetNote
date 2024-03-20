use serde_json::Value;
use std::fs::{File, OpenOptions};
use std::io::{BufReader, BufWriter};
use std::error::Error;

pub fn remove_specific_block(file_path: &str, author: &str, title: &str) -> Result<(), Box<dyn Error>> {
    // Read the file
    let file = File::open(file_path)?;
    let reader = BufReader::new(file);
    let json: Value = serde_json::from_reader(reader)?;

    let mut result = Vec::new();

    if let Value::Array(blocks) = json {
        for block in blocks {
            if let Value::Object(map) = block {
                if map.get("author") != Some(&Value::String(author.to_string())) || map.get("title") != Some(&Value::String(title.to_string())) {
                    result.push(Value::Object(map));
                }
            }
        }
    }

    // Write the result back to the file
    let file = OpenOptions::new().write(true).truncate(true).open(file_path)?;
    let writer = BufWriter::new(file);
    serde_json::to_writer(writer, &Value::Array(result))?;

    Ok(())
}
