use serde_json::Value;
use std::fs::File;
use std::io::BufReader;
use std::error::Error;

pub fn filter_by_author(file_path: &str, author: &str) -> Result<Vec<Value>, Box<dyn Error>> {
    let file = File::open(file_path)?;
    let reader = BufReader::new(file);
    let json: Value = serde_json::from_reader(reader)?;

    let mut result = Vec::new();

    if let Value::Array(blocks) = json {
        for block in blocks {
            if let Value::Object(map) = block {
                if map.get("author") == Some(&Value::String(author.to_string())) {
                    result.push(Value::Object(map));
                }
            }
        }
    }

    Ok(result)
}