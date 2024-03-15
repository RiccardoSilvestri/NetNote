use crate::connection::handle_json::handle_json;
use serde_json::Value;
use crate::connection::user::user_exists::user_exists;

fn get_username(json: &str) -> String {
    let v: Value = serde_json::from_str(json).expect("Invalid JSON");
    let username = v["Name"].as_str().unwrap_or_else(|| "Username not found").to_string();
    println!("{}", username);
    username
}

pub fn register(received : String) -> String {
    let username = get_username(&received);
    let check = user_exists(username);
    println!("{}", check);
    if check{
        return "User already exists".to_string();
    }
    handle_json(received.to_string(), "users.json")
}