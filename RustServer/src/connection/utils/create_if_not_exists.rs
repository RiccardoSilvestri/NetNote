use std::fs;
use std::io::{Write, Read};
use std::io::Result;
use std::io::Error;
use std::io::ErrorKind;

pub fn create_if_not_exists(file_path: &str) -> Result<()> {
    // Check if the file exists
    if fs::metadata(file_path).is_err() {
        // If the file does not exist, create it
        let mut file = fs::File::create(file_path)?;
        // Write "[]" to the file
        file.write_all(b"[]")?;
    }

    // Open the file in read-only mode
    let mut file = fs::File::open(file_path)?;
    let mut contents = String::new();
    // Read the file's contents into a string
    file.read_to_string(&mut contents)?;

    // Check if the file starts with [ and ends with ]
    if !contents.starts_with('[') || !contents.ends_with(']') {
        return Err(Error::new(ErrorKind::InvalidData, "File does not start with [ or end with ]"));
    }

    Ok(())
}
