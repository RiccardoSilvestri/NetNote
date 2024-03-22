use std::sync::{Arc, Mutex};
use serde_json::{Value};
use super::get_credentials::*;

use crate::connection::handle_json::write_json;
use crate::connection::user::user_exists::user_exists;

pub fn register(received : String, file_access: Arc<Mutex<()>>) -> Result<String, JsonCustomError> {
    let v: Value = serde_json::from_str(&received).map_err(JsonCustomError::from)?;

    if !v.is_object() {
        return Err(JsonCustomError::InvalidJson("Expected a JSON object".to_string()));
    }

    let obj = v.as_object().unwrap();

    if obj.len() != 2 || !obj.contains_key("name") || !obj.contains_key("password") {
        return Err(JsonCustomError::InvalidJson("JSON must only contain 'name' and 'password'".to_string()));
    }

    let username = obj.get("name").unwrap().as_str().unwrap();
    let check = user_exists(username.to_string());

    if check {
        return Err(JsonCustomError::InvalidJson("User already exists".to_string()));
    }

    write_json(received, "users.json", file_access);
    return Ok("Successfully registered".to_string())
}
