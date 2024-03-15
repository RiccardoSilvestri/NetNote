use crate::connection::handle_json::handle_json;
use crate::connection::user::user_exists::user_exists;
use crate::connection::user::get_credentials::*;

pub fn register(received : String) -> String {
    let username = get_username(&received);
    let check = user_exists(username);
    println!("{}", check);
    if check{
        return "User already exists".to_string();
    }
    handle_json(received.to_string(), "users.json")
}