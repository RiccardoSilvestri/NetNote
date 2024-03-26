use std::fs;
use std::io::Result;

pub fn create_if_not_exists(file_path: &str) -> Result<()> {
    // Check if the file exists
    if fs::metadata(file_path).is_err() {
        // If the file does not exist, create it
        fs::File::create(file_path)?;
    }
    Ok(())
}