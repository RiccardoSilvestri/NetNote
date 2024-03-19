use serde_json::{Value, Error};
use std::fmt;

#[derive(Debug)]
pub enum CustomError {
    InvalidJson(String),
    SerdeJson(Error),
}

impl fmt::Display for CustomError {
    fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
        match self {
            CustomError::InvalidJson(msg) => write!(f, "Invalid JSON: {}", msg),
            CustomError::SerdeJson(err) => write!(f, "Serde JSON Error: {}", err),
        }
    }
}

impl From<Error> for CustomError {
    fn from(err: Error) -> CustomError {
        CustomError::SerdeJson(err)
    }
}

use crate::connection::handle_json::handle_json;
use crate::connection::user::user_exists::user_exists;

pub fn register(received : String) -> Result<String, CustomError> {
    let v: Value = serde_json::from_str(&received).map_err(CustomError::from)?;

    if !v.is_object() {
        return Err(CustomError::InvalidJson("Expected a JSON object".to_string()));
    }

    let obj = v.as_object().unwrap();

    if obj.len() != 2 || !obj.contains_key("name") || !obj.contains_key("password") {
        return Err(CustomError::InvalidJson("JSON must only contain 'name' and 'password'".to_string()));
    }

    let username = obj.get("name").unwrap().as_str().unwrap();
    let check = user_exists(username.to_string());

    if check {
        return Ok("User already exists".to_string());
    }

    handle_json(received, "users.json");
    return Ok("Successfully registered".to_string())
}
