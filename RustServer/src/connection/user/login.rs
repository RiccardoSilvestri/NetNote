use serde_json::{Value,};
use crate::connection::user::user_exists::user_exists;
use sha2::{Sha256, Digest};
use super::get_credentials::*;

fn get_hash(text :String) -> String{
    let mut hasher = Sha256::new();
    hasher.update(text);
    return hasher.finalize().iter().map(|b| format!("{:02x}", b)).collect();
}

pub fn login(received : String) -> Result<String, CustomError> {
    let v: Value = serde_json::from_str(&received).map_err(CustomError::from)?;

    if !v.is_object() {
        return Err(CustomError::InvalidJson("Expected a JSON object".to_string()));
    }

    let obj = v.as_object().unwrap();

    if obj.len() != 2 || !obj.contains_key("name") || !obj.contains_key("password") {
        return Err(CustomError::InvalidJson("JSON must only contain 'name' and 'password'".to_string()));
    }

    let username = obj.get("name").unwrap().as_str().unwrap();
    let password = obj.get("password").unwrap().as_str().unwrap();

    let check = user_exists(username.to_string());

    if !check {
        return Err(CustomError::InvalidJson("User does not exist".to_string()));
    }

    let mut password_stored = "".to_string();
    match get_password_from_file(username, "users.json") {
        Ok(pw) => {
            println!("Password: {}", pw);
            password_stored = pw;
        },
        Err(error) => println!("Error: {}", error),
    }
    return if get_hash(password.to_string()).eq(&get_hash(password_stored)) {
        Ok("Successfully logged in".to_string())
    } else {
        Err(CustomError::InvalidJson("Incorrect password".to_string()))
    }
}
