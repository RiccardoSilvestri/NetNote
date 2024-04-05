use std::fs;
use std::io::{Write};
use std::io::Result;

pub fn create_if_not_exists(file_path: &str) -> Result<()> {
    // Check if the file exists
    if fs::metadata(file_path).is_err() {
        // If the file does not exist, create it
        let mut file = fs::File::create(file_path)?;
        // Write "[]" to the file
        file.write_all(b"[]")?;
    }
    Ok(())
}
